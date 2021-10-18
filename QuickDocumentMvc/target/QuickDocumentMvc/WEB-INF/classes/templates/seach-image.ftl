<#import "main-template.ftl" as layout>
<@layout.commonLayout; section>
    <#if section = "breadcrumb">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${url.homeUrl}">${msg("home")}</a></li>
                <li class="breadcrumb-item active" aria-current="page">${msg("doSearchImage")}</li>
            </ol>
        </nav>      
    <#elseif section = "formHeader">
        ${msg("searchYourImage")}
    <#elseif section = "form">
        <form id="qd-search-image-form" class="" action="${url.searchImageUrl}" method="post">

             <div class="row mb-sm-2 justify-content-center">
                <div class="col-md-9 d-flex align-items-center">
                    <div class="mx-2">
                        <span class="font-weight-bolder">${msg("tapingKeywordToFindYourImage")}</span>
                   </div>
                   <div class="tooltip-info" data-bs-toggle="tooltip" data-bs-placement="top" title="${msg("youCanTypingManyKeywordSeparatedByComma")}">
                        <img   src="${url.resourcesUrl}/img/svg/tooltip-icon.svg"> 
                    </div>
                </div>
            </div>

            <div class="row mb-sm-2 justify-content-center">
                <div class="col-sm-9">
                    <!-- Form-label-group -->
                    <div class="form-label-group">
                        <input tabindex="1" class="form-control py-2" type="text" id="keyword-image"  name="keyword-image">
                    </div>
                    <!-- Form-label-group -->
                </div>
            </div>
            
            <div class="row justify-content-center mt-4">

                <div class="col-sm-6">
                   <button tabindex="2" id="qd-search-image-btn" class="btn w-100 cm-btn-rounded py-3 text-white font-weight-bold qd-secondary-bd-color qd-btn-loading" type="submit" disabled>${msg("doSearch")}</button>
                </div>

            </div>

        </form>
    </#if>
</@layout.commonLayout>
