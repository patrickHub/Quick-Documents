<!--Navbar-->
<nav class="navbar bg-white navbar-expand-lg fixed-top global-header__wrapper--logo-menus public-logo-header hm-primary-color">
    <div class="container">

            <a class="navbar-brand">
                 <img src="${url.resourcesUrl}/img/quickdocument_logo.png" class="mr-2" alt="" width="184px">
            </a>

            
            <!-- Left Navbar Links -->
            <ul class="navbar-nav mr-auto smooth-scroll">
                <li class="nav-item">
                    <!--<a class="nav-link" href="#intro"><i class="fas fa-home"></i></a>-->
                </li>
            </ul>

            <!-- Rigth Navbar Links -->
            <ul class="navbar-nav nav-flex-icons">
            
            
            <!-- Supported Locale Links -->
            <#if locale.supported?size gt 0>
                <li class="nav-item dropdown d-flex align-items-center">
                    <a class="dropdown-toggle qd-primary-color" id="navbarDropdownLocal" data-bs-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-globe-americas mr-0"></i> ${locale.current} 
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownLocal">
                        <#list locale.supported as l>
                            <a class="dropdown-item" href="${l.url}">
                                <img src="${url.resourcesUrl}/img/flag/${l.url[l.url?last_index_of("=") + 1..]?lower_case + "_16.png"}" class="mr-2" alt="">
                                ${l.label}
                                <#if locale.current == l.label>
                                    <i class="fa fa-check ml-4"></i>
                                </#if>
                                
                            </a>
                        </#list>
                    </div>
                </li>
            </#if>
        
    </div>

</nav>
<!--Navbar-->