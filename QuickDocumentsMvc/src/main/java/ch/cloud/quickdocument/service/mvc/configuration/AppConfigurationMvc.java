package ch.cloud.quickdocument.service.mvc.configuration;

import ch.cloud.quickdocument.service.dam.utilities.Utility;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class AppConfigurationMvc {

  private static final String PROD_BASE_URI = "https://wwww.quickdocument.ch/QuickDocumentService";
  private static final String TEST_BASE_URI = "http://127.0.0.1:8070/QuickDocumentService";

  public static String getBaseUri() {

    if (isLocalDeployment()) {
      return TEST_BASE_URI;
    }

    return PROD_BASE_URI;
  }


  private AppConfigurationMvc() {}



  public static boolean isLocalDeployment() {
    return Utility.isLocalDeployment();
  }

}
