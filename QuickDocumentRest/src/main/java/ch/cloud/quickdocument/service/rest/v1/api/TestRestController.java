package ch.cloud.quickdocument.service.rest.v1.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for testing rest api
 * 
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/api/v1/test")
public class TestRestController {



  /**
   * Returns ciao
   *
   * @return 200 OK String
   */
  @GetMapping("/ciao")
  public String getTest() {
    return "ciao";
  }



}
