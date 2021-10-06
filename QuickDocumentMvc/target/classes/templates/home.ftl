<#import "main-template.ftl" as layout>
<@layout.commonLayout; section>
    <#if section = "form">

    	<div class="row mb-sm-4 flex-center mt-4">

                <div class="col-sm-6 mb-3">
                   <a href="${url.uploadImageUrl}"  class="btn btn-white w-100 pt-sm-3 py-3 cm-btn-rounded border-style-solid indigo-text fw-bold border-2 qd-secondary-border-color qd-btn-loading">${msg("doUploadImage")}</a>
                </div>

                <div class="col-sm-6 mb-2">
                   <a href="${url.searchImageUrl}"  class="btn btn-white w-100 pt-sm-3 py-3 cm-btn-rounded border-style-solid indigo-text fw-bold border-2 qd-primary-border-color qd-btn-loading">${msg("doSearchImage")}</a>
                </div>
        </div>

    </#if>
</@layout.commonLayout>