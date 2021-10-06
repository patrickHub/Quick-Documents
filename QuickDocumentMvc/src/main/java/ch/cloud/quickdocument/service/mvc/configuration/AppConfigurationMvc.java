package ch.cloud.quickdocument.service.mvc.configuration;

import ch.cloud.quickdocument.service.dam.utilities.Utility;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class AppConfigurationMvc {

  private static final String PROD_BASE_URI = "https://wwww.quickdocument.ch/QuickDocumentMvc";
  private static final String TEST_BASE_URI = "http://127.0.0.1:8070/QuickDocumentMvc";

  private static final String PROD_BASE_REST_URI = "http://127.0.0.1:8070/QuickDocumentRest-1.0.0";
  private static final String TEST_BASE_REST_URI = "http://127.0.0.1:8070/QuickDocumentRest-1.0.0";

  public static String getBaseUri() {

    if (isLocalDeployment()) {
      return TEST_BASE_URI;
    }

    return PROD_BASE_URI;
  }


  public static String getBaseRestUri() {

    if (isLocalDeployment()) {
      return TEST_BASE_REST_URI;
    }

    return PROD_BASE_REST_URI;
  }


  private AppConfigurationMvc() {}



  public static boolean isLocalDeployment() {
    return Utility.isLocalDeployment();
  }

}
