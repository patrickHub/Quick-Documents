<#import "main-template.ftl" as layout>
<@layout.commonLayout; section>
    <#if section = "breadcrumb">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${url.homeUrl}">${msg("home")}</a></li>
                <li class="breadcrumb-item active" aria-current="page">${msg("searchImageResult")}</li>
            </ol>
        </nav>      
    <#elseif section = "formHeader">
        ${msg("searchImageResult")}
    <#elseif section = "form">

            <#if searchImages?size gt 0>
                <div class="jcarousel-wrapper">
                    <div class="jcarousel" data-jcarousel="true">
                        <ul>
                            <#list searchImages as foundImage>

                                <li><a href="${foundImage.imgPath}" data-lity data-lity-desc="Photo of a search"><img src="${foundImage.imgPath}" alt="${foundImage.imgPath}"></a></li>
                                                        
                            </#list>
                        </ul>
                    </div>
                    <a href="#" class="jcarousel-control-prev" data-jcarouselcontrol="true">&laquo</a>
                    <a href="#" class="jcarousel-control-next" data-jcarouselcontrol="true">&raquo</a>

                    <p class="jcarousel-pagination" data-jcarouselpagination="true">
                        <#list searchImages as foundImage>
                            <a href="" class=""></a>
                        </#list>
                    </p>
                </div>

            <#else>
                <p class="font-weight-bold">${msg("sorryNotImageFoundPleaseTryAgain")}</p>
                <p><a href="${url.searchImageUrl}">${msg("backToResearch")}</a></p>
                
            </#if>


            
    </#if>
</@layout.commonLayout>
