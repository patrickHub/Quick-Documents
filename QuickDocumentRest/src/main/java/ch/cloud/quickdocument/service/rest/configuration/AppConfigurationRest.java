package ch.cloud.quickdocument.service.rest.configuration;

import ch.cloud.quickdocument.service.rest.utilities.Utility;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class AppConfigurationRest {

  private static final String TEST_BASE_URI = "http://127.0.0.1:8080/QuickDocumentRest-1.0.0";

  public static String getBaseUri() {

    if (isLocalDeployment()) {
      return TEST_BASE_URI;
    }

    return "http://" + System.getenv("QUICKDOCUMENT_REST_SERVER") + ":8080/QuickDocumentRest-1.0.0";
  }


  private AppConfigurationRest() {}



  public static boolean isLocalDeployment() {
    return Utility.isLocalDeployment();
  }

}
