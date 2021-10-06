<#import "main-template.ftl" as layout>
<@layout.commonLayout; section>
    <#if section = "breadcrumb">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${url.homeUrl}">${msg("home")}</a></li>
                <li class="breadcrumb-item active" aria-current="page">${msg("doUploadImage")}</li>
            </ol>
        </nav>      
    <#elseif section = "formHeader">
        ${msg("thankYouTitle")}
    <#elseif section = "form">
        <div class="row mb-4 mt-4 flex-center text-center">
            <div class="col">
                <div>
                    <img class=""  src="${url.resourcesUrl}/img/svg/qd_check_success.svg">
                </div>
        </div>
        </div>

        <div class="row mt-4 text-center">
            <div class="col">
                <p class="text-justify font-weight-bolder mb-4">${msg("yourImageHasBeenUploadedSuccessfully")}<p>
            </div> 
        </div>
    </#if>
</@layout.commonLayout>
