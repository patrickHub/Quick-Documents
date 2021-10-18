package ch.cloud.quickdocument.service.rest.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class RestClientUtil {


  private static final String ImdsServer = "http://169.254.169.254";
  private static final String publicAddressIpEndpoint =
      ImdsServer + "/metadata/instance/network/interface/0/ipv4/ipAddress/0/publicIpAddress?api-version=2021-02-01&format=text";

  public static String queryPulbicIpAddressEndpoint() {
    return queryImds(publicAddressIpEndpoint);
  }

  private static String queryImds(String path) {

    try {
      URL url = new URL(path);

      HttpURLConnection con = (HttpURLConnection) url.openConnection();

      con.setRequestMethod("GET");
      con.setRequestProperty("Metadata", "True");
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String line;
      StringBuffer response = new StringBuffer();

      while ((line = in.readLine()) != null) {
        response.append(line);
      }
      in.close();
      return response.toString();
    } catch (Exception ex) {

      ex.printStackTrace();

      return "";
    }
  }



}
