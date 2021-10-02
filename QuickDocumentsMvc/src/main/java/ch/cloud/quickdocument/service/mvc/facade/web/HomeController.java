package ch.cloud.quickdocument.service.mvc.facade.web;


import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ch.cloud.quickdocument.service.dam.adapter.ImageManager;
import ch.cloud.quickdocument.service.dam.adapter.ImageTextManager;
import ch.cloud.quickdocument.service.dam.model.Dtos.ImageDTO;
import ch.cloud.quickdocument.service.dam.model.Dtos.ImageTextDTO;
import ch.cloud.quickdocument.service.locale.model.entities.SupportedLocale;
import ch.cloud.quickdocument.service.mvc.configuration.Constants;
import ch.cloud.quickdocument.service.mvc.configuration.Urls;
import ch.cloud.quickdocument.service.mvc.handler.FreeMarkerPageHandler;
import ch.cloud.quickdocument.service.mvc.utilities.FileImageUtil;
import net.sourceforge.tess4j.TesseractException;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

@CrossOrigin
@Controller
public class HomeController {

  private static final Logger LOG = Logger.getLogger(HomeController.class);

  public static final String HOME_TEMPLATE = "home.ftl";

  public static final String UPLOAD_IMAGE_TEMPLATE = "upload-image.ftl";
  public static final String UPLOAD_IMAGE_SUCCESS_TEMPLATE = "upload-image-success.ftl";

  public static final String SEARCH_IMAGE_TEMPLATE = "seach-image.ftl";

  public static final String SEARCH_IMAGE_RESULT_TEMPLATE = "seach-image-result.ftl";

  @Autowired
  private FreeMarkerPageHandler freeMarkerPageHandler;

  @Autowired
  private ImageManager imageManager;

  @Autowired
  private ImageTextManager imageTextManager;


  @GetMapping(value = "/")
  public ResponseEntity<String> home(@RequestAttribute(Constants.QD_LOCALE) SupportedLocale supportedLocale) {

    Locale currentLocale = new Locale(supportedLocale.getLocale());

    freeMarkerPageHandler.setLocale(currentLocale);
    freeMarkerPageHandler.setUriComponents(Urls.getHome());
    freeMarkerPageHandler.addAttribute(Constants.TITLE, Constants.QUICK_DOCUMENT_TITLE);



    String body = freeMarkerPageHandler.processTemplate(HOME_TEMPLATE);
    return ResponseEntity.ok().body(body);

  }


  @GetMapping(value = "/v1/images/upload")
  public ResponseEntity<String> uploadImage(@RequestAttribute(Constants.QD_LOCALE) SupportedLocale supportedLocale) {

    Locale currentLocale = new Locale(supportedLocale.getLocale());

    freeMarkerPageHandler.setLocale(currentLocale);
    freeMarkerPageHandler.setUriComponents(Urls.uploadImage());
    freeMarkerPageHandler.addAttribute(Constants.TITLE, Constants.QUICK_DOCUMENT_TITLE);

    String body = freeMarkerPageHandler.processTemplate(UPLOAD_IMAGE_TEMPLATE);

    return ResponseEntity.ok().body(body);

  }

  @PostMapping(value = "/v1/images/upload")
  public ResponseEntity<String> saveImage(@RequestParam(Constants.IMAGE) MultipartFile imageFile,
      @RequestAttribute(Constants.QD_LOCALE) SupportedLocale supportedLocale) {

    String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());

    String body = null;
    Locale currentLocale = new Locale(supportedLocale.getLocale());
    freeMarkerPageHandler.setLocale(currentLocale);
    freeMarkerPageHandler.setUriComponents(Urls.uploadImage());
    freeMarkerPageHandler.addAttribute(Constants.TITLE, Constants.QUICK_DOCUMENT_TITLE);


    try {

      // register image in file system
      Path filePath = FileImageUtil.saveImageInFileSystem(imageFile);

      // get image content
      String content = FileImageUtil.readTextFromImage(filePath);

      // save image entity in MySql Db and get id
      ImageDTO imageDTO = new ImageDTO();
      imageDTO.setImgPath(filePath.toFile().getName());
      int imageId = imageManager.saveImage(imageDTO);

      // save
      ImageTextDTO imageTextDTO = new ImageTextDTO();
      imageTextDTO.setContent(content);

      imageDTO.setId(imageId);
      imageTextDTO.setImageDTO(imageDTO);
      imageTextManager.saveImageText(imageTextDTO);

      body = freeMarkerPageHandler.processTemplate(UPLOAD_IMAGE_SUCCESS_TEMPLATE);

    } catch (IOException e) {

      freeMarkerPageHandler.addAttribute(Constants.UPLOAD_IMAGE_FAILED, Constants.UPLOAD_IMAGE_FAILED);
      body = freeMarkerPageHandler.processTemplate(UPLOAD_IMAGE_TEMPLATE);

      LOG.info("Unable to create image " + imageFile.getOriginalFilename());
    } catch (TesseractException e) {

      freeMarkerPageHandler.addAttribute(Constants.UPLOAD_IMAGE_FAILED, Constants.UPLOAD_IMAGE_FAILED);
      body = freeMarkerPageHandler.processTemplate(UPLOAD_IMAGE_TEMPLATE);
      LOG.info("Unable to read text from image " + fileName);
    }

    return ResponseEntity.ok().body(body);

  }


  @GetMapping(value = "/v1/images/search")
  public ResponseEntity<String> searchImage(@RequestAttribute(Constants.QD_LOCALE) SupportedLocale supportedLocale) {

    Locale currentLocale = new Locale(supportedLocale.getLocale());

    freeMarkerPageHandler.setLocale(currentLocale);
    freeMarkerPageHandler.setUriComponents(Urls.searchImage());
    freeMarkerPageHandler.addAttribute(Constants.TITLE, Constants.QUICK_DOCUMENT_TITLE);

    String body = freeMarkerPageHandler.processTemplate(SEARCH_IMAGE_TEMPLATE);

    return ResponseEntity.ok().body(body);

  }

  @PostMapping(value = "/v1/images/search")
  public ResponseEntity<String> searchImageResult(HttpServletRequest request, @RequestAttribute(Constants.QD_LOCALE) SupportedLocale supportedLocale) {

    String keyword = request.getParameter(Constants.KEYWORD_IMAGE);

    Locale currentLocale = new Locale(supportedLocale.getLocale());

    freeMarkerPageHandler.setLocale(currentLocale);
    freeMarkerPageHandler.setUriComponents(Urls.searchImage());
    freeMarkerPageHandler.addAttribute(Constants.TITLE, Constants.QUICK_DOCUMENT_TITLE);
    freeMarkerPageHandler.addAttribute(Constants.FILES, Constants.FILES);
    freeMarkerPageHandler.addAttribute(Constants.SEARCH_IMAGE, imageManager.searchImageByText(keyword.replace(",", " ")));

    String body = freeMarkerPageHandler.processTemplate(SEARCH_IMAGE_RESULT_TEMPLATE);

    return ResponseEntity.ok().body(body);

  }



}

