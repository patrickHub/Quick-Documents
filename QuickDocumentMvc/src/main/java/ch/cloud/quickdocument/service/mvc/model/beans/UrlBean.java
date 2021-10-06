package ch.cloud.quickdocument.service.mvc.model.beans;

import ch.cloud.quickdocument.service.mvc.configuration.Urls;

/**
 * {@code UrlBean} class is a Bean class that will be send as url attribute in {@link ch.cloud.quickdocument.service.mvc.handler.FreeMarkerPageHandler FreeMarkerPageHandler} class
 * before processing any template.
 * 
 * <p>
 * It contains the url of any available useful page that will available on each template to enable user to navigate through one page to another.
 * </p>
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class UrlBean {

  /**
   * {@code UrlBean} class is a Bean class that will be send as url attribute in {@link ch.cloud.quickdocument.service.mvc.handler.FreeMarkerPageHandler FreeMarkerPageHandler} class
   * before processing any template.
   * 
   * <p>
   * It contains the url of any available useful page that will available on each template to enable user to navigate through one page to another.
   * </p>
   * 
   * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
   *
   */
  public UrlBean() {}

  public String getResourcesUrl() {

    return Urls.uriBase().build().toUriString();
  }

  public String getHomeUrl() {

    return Urls.getHome().toUriString();
  }

  public String getUploadImageUrl() {

    return Urls.uploadImage().toUriString();
  }


  public String getSearchImageUrl() {

    return Urls.searchImage().toUriString();
  }

}
