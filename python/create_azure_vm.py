import azure.mgmt.compute
import azure.mgmt.network
import azure.mgmt.resource
import azure.mgmt.storage
import msrestazure.azure_active_directory


import time
import random
import string
import os
from base64 import b64decode

from azure.mgmt.compute.models import DiskCreateOption
from azure.mgmt.compute import ComputeManagementClient
from azure.mgmt.network import NetworkManagementClient
from azure.mgmt.resource.resources import ResourceManagementClient
from azure.mgmt.storage import StorageManagementClient
from msrestazure.azure_active_directory import UserPassCredentials

PASSWORD = os.getenv('AZURE_PASSWORD')
USERNAME = os.getenv("AZURE_USERNAME")
SUBSCRIPTION_ID = os.getenv("AZURE_SOURSCRIPTION_ID")
AZURE_CLIENT_ID = os.getenv("AZURE_CLIENT_ID")
AZURE_CLIENT_SECRET = os.getenv("AZURE_CLIENT_SECRET")

credentials = UserPassCredentials(
    USERNAME,
    PASSWORD,
    client_id=AZURE_CLIENT_ID,
    secret=AZURE_CLIENT_SECRET
)

compute_client = ComputeManagementClient(
    credentials,
    SUBSCRIPTION_ID
)

network_client = NetworkManagementClient(
    credentials,
    SUBSCRIPTION_ID
)

resource_client = ResourceManagementClient(
    credentials,
    SUBSCRIPTION_ID
)

storage_client = StorageManagementClient(
    credentials,
    SUBSCRIPTION_ID
)

# Common parameters
OS_DISK_TYPE = 'Premium_LRS'

# REST parameters
VM_NAME_REST = "srv-cloud-vm-rest"
GROUP_NAME_REST = 'cloud-resource-group-rest'
VNET_NAME_REST = 'cloud-resource-vnet-rest'
SUBNET_NAME_REST = 'subnet-1x'
NIC_NAME_REST = VM_NAME_REST + '698'
NSG_NAME_REST = VM_NAME_REST + '-nsg'
PUBLIC_IP_NAME_REST = VM_NAME_REST + '-ip'
REGION_REST = 'switzerlandnorth'
IMAGE_REFERENCE_ID_REST = '/subscriptions/a57a4565-adc2-483b-86dc-1a9623bc5444/resourceGroups/cloud-resource-group-rest/providers/Microsoft.Compute/galleries/cloud_resource_galerie_rest/images/img-def-rest/versions/1.0.0'


# MVC parameters
VM_NAME_MVC = "srv-cloud-vm-mvc"
GROUP_NAME_MVC = 'cloud-resource-group-mvc'
VNET_NAME_MVC = 'cloud-resource-vnet-mvc'
SUBNET_NAME_MVC = 'subnet-2x'
NIC_NAME_MVC = VM_NAME_MVC + '726'
NSG_NAME_MVC = VM_NAME_MVC + '-nsg'
PUBLIC_IP_NAME_MVC = VM_NAME_MVC + '-ip'
REGION_MVC = 'germanywestcentral'
IMAGE_REFERENCE_ID_MVC = '/subscriptions/a57a4565-adc2-483b-86dc-1a9623bc5444/resourceGroups/cloud-resource-group-mvc/providers/Microsoft.Compute/galleries/cloud_resource_galery_mvc/images/img-def-mvc/versions/1.0.0'


# DB parameters
VM_NAME_DB= "srv-cloud-vm-db"
GROUP_NAME_DB = GROUP_NAME_MVC 
VNET_NAME_DB = VNET_NAME_MVC
SUBNET_NAME_DB= 'subnet-1x'
NIC_NAME_DB= VM_NAME_DB + '218'
NSG_NAME_DB = VM_NAME_DB + '-nsg'
PUBLIC_IP_NAME_DB= VM_NAME_DB + '-ip'
COMPUTER_NAME_DB= VM_NAME_MVC
REGION_DB= REGION_MVC
IMAGE_REFERENCE_ID_DB= '/subscriptions/a57a4565-adc2-483b-86dc-1a9623bc5444/resourceGroups/cloud-resource-group-mvc/providers/Microsoft.Compute/galleries/cloud_resource_galery_mvc/images/img-def-db/versions/1.0.0'


def get_vnet_subnet(network_client, group_name, network_name, subnet_name):
    """Get existing subnet of existing virtual network
    :param network_client: azure compute network client api
    :param group_name: group name of the vnet
    :param network_name: name of vnet
    :param subnet_name: name of the subnet
    return: The subnet object
    """
    # get subnet
    subnet = network_client.subnets.get(group_name, network_name, subnet_name)
    return subnet

def create_network_security_group(network_client, group_name, nsg_name, region):
    """Create network security group with two custom rules
    :param network_client: azure compute network client api
    :param group_name: group name of the vnet
    :param nsg_name: name of nsg
    :param region: nsg location
    return: The nsg object
    """
    parameters = azure.mgmt.network.models.NetworkSecurityGroup()
    parameters.location = region
    parameters.security_rules =  [
            azure.mgmt.network.models.SecurityRule(
                name='Inbound_Port_3389',
                access=azure.mgmt.network.models.SecurityRuleAccess.allow,
                description='RDP security rule',
                destination_address_prefix='*',
                destination_port_range='3389',
                direction=azure.mgmt.network.models.SecurityRuleDirection.inbound,
                priority=340,
                protocol=azure.mgmt.network.models.SecurityRuleProtocol.tcp,
                source_address_prefix='*',
                source_port_range='*',
            ),
             azure.mgmt.network.models.SecurityRule(
                name='Inbound_Port_8080',
                access=azure.mgmt.network.models.SecurityRuleAccess.allow,
                description='Wildfly security rule',
                destination_address_prefix='*',
                destination_port_range='8080',
                direction=azure.mgmt.network.models.SecurityRuleDirection.inbound,
                priority=350,
                protocol=azure.mgmt.network.models.SecurityRuleProtocol.tcp,
                source_address_prefix='*',
                source_port_range='*',
            )
            
        ]

    network_client.network_security_groups.create_or_update(
        group_name, 
        nsg_name,
        parameters       
    )
    nsg =  network_client.network_security_groups.get(group_name, nsg_name)
    return nsg


def create_public_ip(network_client, region, group_name, ip_name):
    """Creates new static public ip.
    :param network_client: azure compute network client api
    :param region: location of the ip
    :param group_name: group name of the ip
    :param ip_name: ip name
    return: The public ip object
    """

    # create public ip address
    result = network_client.public_ip_addresses.create_or_update(
        group_name,
        ip_name,
        azure.mgmt.network.models.PublicIPAddress(
            location=region,
            public_ip_allocation_method=azure.mgmt.network.models.IPAllocationMethod.static
        ),
    )
    result.wait()
    public_ip_address = network_client.public_ip_addresses.get(group_name, ip_name)
    return public_ip_address


def create_network_interface(network_client, region, group_name, interface_name, network_name, subnet_name, ip_name, nsg_name, with_public_ip=True, with_nsg=True):
    """Creates new network interface (NIC).
    return: The network interface object
    """
    # first we get subnet
    subnet = get_vnet_subnet(network_client, group_name, network_name, subnet_name)

    # secondy we create public ip address in the new vnet
    public_ip_address = create_public_ip(network_client, region, group_name, ip_name) if with_public_ip else None

    # create a network security group
    nsg = create_network_security_group(network_client, group_name, nsg_name, region) if with_nsg else None
    
    
    # finally we create network interface with its subnet and public ip address if any
    result = network_client.network_interfaces.create_or_update(
        group_name,
        interface_name,
        azure.mgmt.network.models.NetworkInterface(
            name=interface_name,
            location=region,
            ip_configurations=[
                azure.mgmt.network.models.NetworkInterfaceIPConfiguration(
                    name='default',
                    private_ip_allocation_method=azure.mgmt.network.models.IPAllocationMethod.static,
                    subnet=subnet,
                    public_ip_address= azure.mgmt.network.models.PublicIPAddress(
                        id=public_ip_address.id,
                    ) if with_public_ip else None
                ),
            ],
            network_security_group=azure.mgmt.network.models.NetworkSecurityGroup(
                id=nsg.id
            ) if with_nsg else None
        ),
    )
    result.wait()
    network_interface = network_client.network_interfaces.get(
        group_name,
        interface_name,
    )
    return network_interface


def create_vm_parameters(vm_name, region, image_reference_id, nic_id,  user_data):
    """Creates vm parameters.
    :param vm_name: vm name
    :param region: vn location
    :param image_reference_id: vn image reference id
    :param nic_id: vm nic id
    :param user_data: mv user_data
    return: The instance of azure.mgmt.compute.models.VirtualMachine
    """
    return azure.mgmt.compute.models.VirtualMachine(
        location=region,
        name=vm_name,
        hardware_profile=azure.mgmt.compute.models.HardwareProfile(
          vm_size="Standard_D2s_v3"
        ),
        network_profile=azure.mgmt.compute.models.NetworkProfile(
            network_interfaces=[
                azure.mgmt.compute.models.NetworkInterfaceReference(
                    id=nic_id,
                ),
            ],
        ),
        storage_profile=azure.mgmt.compute.models.StorageProfile(
            os_disk=azure.mgmt.compute.models.OSDisk(
                create_option=azure.mgmt.compute.models.DiskCreateOptionTypes.from_image,
                managed_disk=azure.mgmt.compute.models.ManagedDiskParameters(
                    storage_account_type=OS_DISK_TYPE
                )
            ),
            image_reference=azure.mgmt.compute.models.ImageReference(id=image_reference_id),
            data_disks={
                azure.mgmt.compute.models.DataDisk(
                    create_option=azure.mgmt.compute.models.DiskCreateOptionTypes.from_image,
                    caching='ReadOnly',
                    write_accelerator_enabled=False
                    
                )
            }
        ),
        userData=user_data
    )

##### Start deployment of the REST vm ##############################################################
# create nic for rest vm
print('Start deployment of REST VM')

nic_rest = create_network_interface(
    network_client,
    REGION_REST,
    GROUP_NAME_REST,
    NIC_NAME_REST,
    VNET_NAME_REST,
    SUBNET_NAME_REST,
    PUBLIC_IP_NAME_REST,
    NSG_NAME_REST
)
print("\nREST VM Network interface created")

# create rest vm
result = compute_client.virtual_machines.create_or_update(
    GROUP_NAME_REST,
    VM_NAME_REST,
    create_vm_parameters(VM_NAME_REST, REGION_REST, IMAGE_REFERENCE_ID_REST, nic_rest.id, "")
)
result.wait()
print('\nREST VM created successfully')


##### Start deployment of the DB vm ##############################################################
# create nic for db vm
print('Start deployment of DB VM')

nic_db = create_network_interface(
    network_client,
    REGION_DB,
    GROUP_NAME_DB,
    NIC_NAME_DB,
    VNET_NAME_DB,
    SUBNET_NAME_DB,
    PUBLIC_IP_NAME_DB,
    NSG_NAME_DB,
    with_public_ip = False,
    with_nsg=False
)
print("\nDB VM Network interface created")

# create db vm
result = compute_client.virtual_machines.create_or_update(
    GROUP_NAME_DB,
    VM_NAME_DB,
    create_vm_parameters(VM_NAME_DB, REGION_DB, IMAGE_REFERENCE_ID_DB, nic_db.id, "")
)
result.wait()
print('\DB VM created successfully')


##### Start deployment of the MVC vm ##############################################################
# create nic for mvc vm
print('Start deployment of MVC VM')

nic_mvc = create_network_interface(
    network_client,
    REGION_MVC,
    GROUP_NAME_MVC,
    NIC_NAME_MVC,
    VNET_NAME_MVC,
    SUBNET_NAME_MVC,
    PUBLIC_IP_NAME_MVC,
    NSG_NAME_MVC
)
print("\nMVC VM Network interface created")
#Format of userData in code: "public_rest_ip, private_db_ip"
userData = '{0}, {1}'.format(nic_rest.ip_configurations[0].public_ip_address, nic_db.ip_configurations[0].private_ip_address)

# create rest vm
result = compute_client.virtual_machines.create_or_update(
    GROUP_NAME_MVC,
    VM_NAME_MVC,
    create_vm_parameters(VM_NAME_MVC, REGION_MVC, IMAGE_REFERENCE_ID_M, nic_mvc.id, userData)
)
result.wait()
print('\nMVC VM created successfully')

