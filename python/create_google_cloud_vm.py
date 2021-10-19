#!/usr/bin/env python

# Copyright 2015 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import argparse
import os
import time

import googleapiclient.discovery
from six.moves import input

# REST SERVER CONFIG
REST_VPC_NAME = 'vpc-rest'
REST_SUBNET_NAME = 'sub-rest'
REST_SUBNET = '10.0.0.0/9'
REST_INSTANCE_NAME = 'machine-rest-1'

# DB and MVC SERVER CONFIG
DB_MVC_VPC_NAME = 'vpc-db-mvc'

DB_INSTANCE_NAME = 'machine-db-1'
DB_SUBNET_NAME = 'sub-db'
DB_SUBNET = '10.1.0.0/16'

MVC_INSTANCE_NAME = 'machine-mvc-1'
MVC_SUBNET_NAME = 'sub-mvc'
MVC_SUBNET = '10.2.0.0/16'


rest_image_name = "image-rest"
db_image_name = "image-db"
mvc_image_name = "image-mvc"

def create_vpc_network(compute, project, name):

    network_body = {
        "autoCreateSubnetworks": False,
        "description": "",
        "mtu": 1460,
        "name": name,
        "routingConfig": {
            "routingMode": "REGIONAL"
        },
    }

    return compute.networks().insert(project=project, body=network_body)


def create_vpc_subnetwork(compute, project, zone, network_name, ip, subnet_name):
    network = f"projects/{project}/global/networks/{network_name}"

    subnetwork_body = {
        "description": "",
        "enableFlowLogs": False,
        "ipCidrRange": ip,
        "name": subnet_name,
        "network": network,
        "privateIpGoogleAccess": False,
        "region": zone,
    }

    return compute.subnetworks().insert(project=project, region=zone, body=subnetwork_body)

def add_firewall_rule(compute, project, name, network, port, range_, tags=[]):
    body = {
        "kind": "compute#firewall",
        "name": name,
        "network": f"projects/{project}/global/networks/{network}",
        "direction": "INGRESS",
        "priority": 1000,
        "targetTags": tags,
        "allowed": [
            {
                "IPProtocol": "tcp",
                "ports": [
                    port
                ]
            },
            {
                "IPProtocol": "udp",
                "ports": [
                    port
                ]
            }
        ],
        "sourceRanges": [
            range_
        ]
    }

    return compute.networks().add_firewall_rule(project=project, body=body)


def create_instance_from_image(compute, project, zone, name, subnetwork, image_name, metadata={}, tags=[]):
    config = {
        "canIpForward": False,
        "confidentialInstanceConfig": {
            "enableConfidentialCompute": False
        },
        "deletionProtection": False,
        "description": "",
        "disks": [
            {
                "autoDelete": True,
                "boot": True,
                "deviceName": name,
                "initializeParams": {
                    "diskSizeGb": "50",
                    "diskType": f"projects/{project}/zones/{zone}/diskTypes/pd-balanced",
                    "labels": {}
                },
                "mode": "READ_WRITE",
                "type": "PERSISTENT"
            }
        ],
        "displayDevice": {
            "enableDisplay": False
        },
        "guestAccelerators": [],
        "labels": {},
        "machineType": f"projects/{project}/zones/{zone}/machineTypes/e2-medium",
        "metadata": metadata,
        "minCpuPlatform": "Automatic",
        "name": name,
        "networkInterfaces": [
            {
                "accessConfigs": [
                    {
                        "name": "External NAT",
                        "networkTier": "PREMIUM"
                    }
                ],
                "subnetwork": f"projects/{project}/regions/{zone}/subnetworks/{subnetwork}"
            }
        ],
        "reservationAffinity": {
            "consumeReservationType": "ANY_RESERVATION"
        },
        "scheduling": {
            "automaticRestart": True,
            "onHostMaintenance": "MIGRATE",
            "preemptible": False
        },
        "serviceAccounts": [
            {
                "email": "548011807589-compute@developer.gserviceaccount.com",
                "scopes": [
                    "https://www.googleapis.com/auth/devstorage.read_only",
                    "https://www.googleapis.com/auth/logging.write",
                    "https://www.googleapis.com/auth/monitoring.write",
                    "https://www.googleapis.com/auth/servicecontrol",
                    "https://www.googleapis.com/auth/service.management.readonly",
                    "https://www.googleapis.com/auth/trace.append"
                ]
            }
        ],
        "shieldedInstanceConfig": {
            "enableIntegrityMonitoring": True,
            "enableSecureBoot": False,
            "enableVtpm": True
        },
        "sourceMachineImage": f"projects/{project}/global/machineImages/{image_name}",
        "tags": tags,
        "zone": zone
    }

    return compute.instances().insert(
        project=project,
        zone=zone,
        body=config).execute()


def wait_for_operation(compute, project, zone, operation):
    print('Waiting for operation to finish...')
    while True:
        result = compute.zoneOperations().get(
            project=project,
            zone=zone,
            operation=operation).execute()

        if result['status'] == 'DONE':
            print("done.")
            if 'error' in result:
                raise Exception(result['error'])
            return result

        time.sleep(1)


def main(project, zone):

    # create and initialize api
    compute = googleapiclient.discovery.build('compute', 'v1')

    # REST SERVER

    print('Creating rest network and sub network')

    # create vpc network
    operation = create_vpc_network(compute, project, REST_VPC_NAME)
    wait_for_operation(compute, project, zone, operation['name'])   # waiting for the end of the operation

    # create vpc subnet
    operation = create_vpc_subnetwork(compute, project, zone, REST_VPC_NAME, REST_SUBNET, REST_SUBNET_NAME)
    wait_for_operation(compute, project, zone, operation['name'])   # waiting for the end of the operation

    # add firewall rules

    # allow traffic on port 8080 (get rest api)
    operation = add_firewall_rule(compute, project, 'rest-allow-8080', REST_VPC_NAME, "8080", "0.0.0.0/0")
    wait_for_operation(compute, project, zone, operation['name'])  # waiting for the end of the operation

    # allow internal communication in the VPC (useless in this case)
    operation = add_firewall_rule(compute, project, 'rest-allow-internal', REST_VPC_NAME, "0-65535", REST_SUBNET)
    wait_for_operation(compute, project, zone, operation['name'])  # waiting for the end of the operation

    print('Creating image of the REST server')

    operation = create_instance_from_image(compute, project, zone, REST_INSTANCE_NAME, REST_SUBNET_NAME, rest_image_name)
    wait_for_operation(compute, project, zone, operation['name'])

    # get external ip, so the mvc server can know dynamically the ip
    response = compute.instances().get(project, zone, instance=DB_INSTANCE_NAME).execute()
    external_ip_rest = response['networkInterfaces'][0]['accessConfigs'][0]['external-ip']

    print('REST server created !')

    # DB server and MVC server

    print('Creating DB and MVC, network and sub network')

    # create vpc network
    operation = create_vpc_network(compute, project, DB_MVC_VPC_NAME)
    wait_for_operation(compute, project, zone, operation['name'])   # waiting for the end of the operation

    # create vpc subnet for DB
    operation = create_vpc_subnetwork(compute, project, zone, DB_MVC_VPC_NAME, DB_SUBNET, DB_SUBNET_NAME)
    wait_for_operation(compute, project, zone, operation['name'])   # waiting for the end of the operation

    # create vpc subnet for MVC
    operation = create_vpc_subnetwork(compute, project, zone, DB_MVC_VPC_NAME, MVC_SUBNET, MVC_SUBNET_NAME)
    wait_for_operation(compute, project, zone, operation['name'])   # waiting for the end of the operation

    # add firewall rules

    # allow traffic on port 8080 (get mvc / website) only for instance with tags : 'mvc' => only the mvc instance (not the db)
    operation = add_firewall_rule(compute, project, 'mvc-allow-8080', REST_VPC_NAME, "8080", "0.0.0.0/0", tags=["mvc"])
    wait_for_operation(compute, project, zone, operation['name'])  # waiting for the end of the operation

    # allow internal communication in the VPC (useless in this case)
    operation = add_firewall_rule(compute, project, 'mvc-db-allow-internal', REST_VPC_NAME, "0-65535", [DB_SUBNET, MVC_SUBNET])
    wait_for_operation(compute, project, zone, operation['name'])  # waiting for the end of the operation

    print('Creating image of the DB server')

    operation = create_instance_from_image(compute, project, zone, DB_INSTANCE_NAME, DB_SUBNET_NAME, db_image_name)
    wait_for_operation(compute, project, zone, operation['name'])

    # get internal ip, so the mvc server can know dynamically the ip
    response = compute.instances().get(project, zone, instance=DB_INSTANCE_NAME).execute()
    internal_ip_db = response['networkInterfaces'][0]['accessConfigs'][0]['natIP']

    print('DB server created !')

    print('Creating image of the MVC server')

    operation = create_instance_from_image(compute, project, zone, MVC_INSTANCE_NAME, MVC_SUBNET_NAME, mvc_image_name, {'userdata' : f'{external_ip_rest}, {internal_ip_db}'}, tags=['mvc'])
    wait_for_operation(compute, project, zone, operation['name'])

    print('MVC server created !')

    print('END of deployment')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description=__doc__,
        formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument('project_id', help='Your Google Cloud project ID.')
    parser.add_argument('--zone', default='europe-central2-a', help='Compute Engine zone to deploy to.')

    args = parser.parse_args()

    main(args.project_id, args.zone)
