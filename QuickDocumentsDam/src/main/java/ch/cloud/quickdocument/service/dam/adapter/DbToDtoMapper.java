package ch.cloud.quickdocument.service.dam.adapter;

import java.util.Objects;
import ch.cloud.quickdocument.service.dam.model.Dtos.ImageTextDTO;
import ch.cloud.quickdocument.service.dam.model.entities.ImageText;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class DbToDtoMapper {



  private DbToDtoMapper() {}


  /**
   * 
   * Method to map imageText entity into imageText Dto
   * 
   * @param  imageText ImageText entity
   * 
   * @return           ImageTextDTO
   * 
   */
  public static ImageTextDTO mapImageTextToDto(ImageText imageText) {

    if (Objects.isNull(imageText)) {
      return null;
    }

    ImageTextDTO imageTextDTO = new ImageTextDTO();

    imageTextDTO.setId(imageText.getId());
    imageTextDTO.setImgPath(imageText.getImgPath());
    imageTextDTO.setContent(imageText.getContent());


    return imageTextDTO;

  }

}
