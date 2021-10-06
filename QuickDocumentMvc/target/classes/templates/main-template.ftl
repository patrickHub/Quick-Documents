<#macro commonLayout bodyClass="" displayInfo=false displayMessage=true displayWide=false>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml" class="">

        <head>
            <meta charset="utf-8">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <meta http-equiv="x-ua-compatible" content="ie=edge">
            <!-- Font Awesome -->
            <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />

            <!-- Google Fonts -->
            <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap">
            <link href="https://fonts.googleapis.com/css2?family=Assistant:wght@400;600;700&family=Gothic+A1:wght@400;600;700&family=Noto+Sans+SC:wght@400;500;700&family=Saira:wght@400;600;700&display=swap" rel="stylesheet">

            <title>${msg(title)}</title>
            <link rel="icon" href="${url.resourcesUrl}/img/favicon.ico" />
            
            <!-- Booststrap core and custom css -->
            <link href="${url.resourcesUrl}/css/bootstrap.min.css" rel="stylesheet">
            <link href="${url.resourcesUrl}/css/mdb.min.css" rel="stylesheet">
            <link href="${url.resourcesUrl}/css/jcarousel.responsive.css" rel="stylesheet">
            <link href="${url.resourcesUrl}/css/lity.css" rel="stylesheet">
            <link href="${url.resourcesUrl}/css/styles.css" rel="stylesheet">
        </head>

        <body id="global-body" class="main-bg-color">
            <header id="" class="mb-xs-3 mb-sm-5 global-header">
                <!-- Navbar Logo Header -->
                	<#include "./commons/public-logo-header.ftl">
                <!-- Navbar Logo Header -->
            </header>
            
            <!-- Main -->
            <main id="" class="login-main-margin-top global-main d-flex justify-content-center align-items-center">
                <div class="container page--homepage text-center"> 
                    <#nested "breadcrumb">         
	                <!-- Card -->
                    <div class="row justify-content-center px-3 hc-main-card">
                        <div class="col-md-7 mb-5 card d-block p-sm-4 <#if searchImages?? && searchImages?size gt 0> main-bg-color</#if>">
                            <!-- Card body -->
                            <div class="card-body pt-3 pb-md-3 px-0 px-sm-4 px-md-4">
                            
                                <!-- Form Header -->
                                <div class="mb-3 mb-sm-5 pt-sm-2 pb-sm-1 text-center">
                                    <h3 id=""><#nested "formHeader"></h3>
                                </div>
                                 <!-- Form Header -->
                                <!-- Form -->
                                <#nested "form">
                                <!-- Form -->
                            </div>
                            	
                            <!-- Card body -->
                            	
                        </div>
                    </div>
                    <!-- Card -->
                </div> 

                <!-- Loading -->
                <div class="qd-secondary-loading justify-content-center align-items-center">
                    <img src="${url.resourcesUrl}/img/gif/rolling-1s-60px.gif" alt="Loading..." />
                </div>
                <!-- Loading -->
                                                                             
            </main>
            <!-- Main -->

            <!--Footer-->
            <footer class="page-footer unique-color-dark">

            	<#include "./commons/public-footer.ftl">
            
            </footer>
            <!-- Footer -->

           	<!-- Bootstrap core and custom Javascript -->
            <script src="${url.resourcesUrl}/js/jquery.min.js" type="text/javascript"></script>
            <script src="${url.resourcesUrl}/js/popper.min.js" type="text/javascript"></script>
            <script src="${url.resourcesUrl}/js/jquery.validate.min.js" type="text/javascript"></script>
            <script src="${url.resourcesUrl}/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="${url.resourcesUrl}/js/jquery.jcarousel.min.js" type="text/javascript"></script>
            <script src="${url.resourcesUrl}/js/jcarousel.responsive.js" type="text/javascript"></script>
            <script src="${url.resourcesUrl}/js/lity.js" type="text/javascript"></script>
            <script src="${url.resourcesUrl}/js/app.js" type="module"></script>
            <!-- Bootstrap core and custom Javascript --> 

        </body>
    </html>
</#macro>
