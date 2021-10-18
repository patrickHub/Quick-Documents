package ch.cloud.quickdocument.service.mvc.utilities;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ch.cloud.quickdocument.service.mvc.configuration.AppConfigurationMvc;
import ch.cloud.quickdocument.service.mvc.configuration.Constants;



/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class RestClientUtil extends ch.cloud.quickdocument.service.dam.utilities.RestClientUtil {

  private RestClientUtil() {}

  public static ResponseEntity<String> postSingleFile(MultipartFile imageFile) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add(Constants.IMAGE, imageFile.getResource());


    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    RestTemplate restTemplate = new RestTemplate();

    return restTemplate.postForEntity(AppConfigurationMvc.getBaseRestUri() + "/v1/images/upload", requestEntity, String.class);

  }

}
