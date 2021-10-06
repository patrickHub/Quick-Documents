package ch.cloud.quickdocument.service.rest.v1.api;


import java.io.IOException;
import java.nio.file.Path;
import org.jboss.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ch.cloud.quickdocument.service.rest.configuration.AppConfigurationRest;
import ch.cloud.quickdocument.service.rest.configuration.Constants;
import ch.cloud.quickdocument.service.rest.utilities.FileImageUtil;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

@CrossOrigin
@RestController
public class FileImageController {

  private static final Logger LOG = Logger.getLogger(FileImageController.class);


  @PostMapping(value = "/v1/images/upload")
  public ResponseEntity<String> saveImage(@RequestParam(Constants.IMAGE) MultipartFile imageFile) {

    try {

      // register image in file system
      Path filePath = FileImageUtil.saveImageInFileSystem(imageFile);

      return ResponseEntity.ok().header(HttpHeaders.LOCATION, AppConfigurationRest.getBaseUri() + "/files/" + filePath.toFile().getName()).body("sucessfull");

    } catch (IOException e) {

      LOG.info("Unable to create image " + imageFile.getOriginalFilename());

      return ResponseEntity.badRequest().body("Image could not be saved");

    }


  }


}

