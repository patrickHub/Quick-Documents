package ch.cloud.quickdocument.service.rest.configuration;

import ch.cloud.quickdocument.service.rest.utilities.Utility;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class AppConfigurationRest {

  private static final String PROD_BASE_URI = "https://wwww.quickdocument.ch/QuickDocumentRest-1.0.0";
  private static final String TEST_BASE_URI = "http://127.0.0.1:8070/QuickDocumentRest-1.0.0";

  public static String getBaseUri() {

    if (isLocalDeployment()) {
      return TEST_BASE_URI;
    }

    return PROD_BASE_URI;
  }


  private AppConfigurationRest() {}



  public static boolean isLocalDeployment() {
    return Utility.isLocalDeployment();
  }

}
