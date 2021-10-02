package ch.cloud.quickdocument.service.dam.adapter;

import java.util.Objects;
import ch.cloud.quickdocument.service.dam.model.Dtos.ImageDTO;
import ch.cloud.quickdocument.service.dam.model.Dtos.ImageTextDTO;
import ch.cloud.quickdocument.service.dam.model.entities.Image;
import ch.cloud.quickdocument.service.dam.model.entities.ImageText;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class DtoToDbMapper {



  private DtoToDbMapper() {}


  /**
   * 
   * Method to map imageText Dto into imageText entity
   * 
   * @param  imageText imageText Dto
   * 
   * @return           imageText
   * 
   */
  public static ImageText mapImageTextDtoToDb(ImageTextDTO imageTextDto) {

    if (Objects.isNull(imageTextDto)) {
      return null;
    }

    ImageText imageText = new ImageText();

    imageText.setContent(imageTextDto.getContent());

    return imageText;

  }



  /**
   * 
   * Method to map image Dto into image entity
   * 
   * @param  imageText image Dto
   * 
   * @return           image
   * 
   */
  public static Image mapImageDtoToDb(ImageDTO imageDto) {

    if (Objects.isNull(imageDto)) {
      return null;
    }

    Image image = new Image();

    image.setImgPath(imageDto.getImgPath());

    return image;

  }

}
