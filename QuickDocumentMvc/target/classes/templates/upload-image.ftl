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
        ${msg("uploadYourImage")}
    <#elseif section = "form">
        <form id="qd-upload-image-form" class="" action="${url.uploadImageUrl}" method="post" enctype="multipart/form-data">

           <#if uploadImageFailed??>
                <div class="row mb-2 justify-content-center">
                    <div class="col-sm-9">
                        <div class="row alert alert-error">
                            <div class="col-2 d-flex justify-content-center">
                                <img class=""  src="${url.resourcesUrl}/img/svg/qd_exclamation_error.svg">
                            </div>
                            <div class="col-10 px-0 text-start">
                                <span>${msg(uploadImageFailed)}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </#if>

            <div class="row mb-sm-2 justify-content-center">
                <div class="col-sm-9">
                    <!-- Form-label-group -->
                    <div class="form-label-group">
                        <input tabindex="1" class="form-control py-2" type="file" id="image"  name="image" accept="image/png, image/gif, image/jpeg">
                    </div>
                    <!-- Form-label-group -->
                </div>
            </div>
            
            <div class="row justify-content-center mt-4">

                <div class="col-sm-6">
                   <button tabindex="2" id="qd-upload-image-btn" class="btn w-100 cm-btn-rounded py-3 text-white font-weight-bold qd-secondary-bd-color qd-btn-loading" type="submit" disabled>${msg("doSave")}</button>
                </div>

            </div>

        </form>
    </#if>
</@layout.commonLayout>
