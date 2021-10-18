package ch.cloud.quickdocument.service.mvc.configuration;

import ch.cloud.quickdocument.service.dam.utilities.Utility;
import ch.cloud.quickdocument.service.mvc.utilities.RestClientUtil;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class AppConfigurationMvc {

  private static final String BASE_URI = Utility.isLocalDeployment() ? "http://127.0.0.1:8080/QuickDocumentMvc"
      : "http://" + RestClientUtil.queryPulbicIpAddressEndpoint() + ":8080/QuickDocumentMvc";

  private static final String BASE_REST_URI = Utility.isLocalDeployment() ? "http://127.0.0.1:8080/QuickDocumentRest-1.0.0"
      : "http://" + RestClientUtil.queryRestUserDataEndpoint() + ":8080/QuickDocumentRest-1.0.0";

  public static String getBaseUri() {

    return BASE_URI;
  }


  public static String getBaseRestUri() {

    return BASE_REST_URI;
  }


  private AppConfigurationMvc() {}



  public static boolean isLocalDeployment() {
    return Utility.isLocalDeployment();
  }

}
