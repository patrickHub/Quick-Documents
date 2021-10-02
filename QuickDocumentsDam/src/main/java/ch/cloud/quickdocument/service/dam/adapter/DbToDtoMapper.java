package ch.cloud.quickdocument.service.dam.adapter;

import java.text.SimpleDateFormat;
import java.util.Objects;
import ch.cloud.quickdocument.service.dam.model.Dtos.ImageDTO;
import ch.cloud.quickdocument.service.dam.model.entities.Image;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class DbToDtoMapper {



  private DbToDtoMapper() {}


  /**
   * 
   * Method to map image entity into image Dto
   * 
   * @param  image Image entity
   * 
   * @return       ImageDTO
   * 
   */
  public static ImageDTO mapImageToDto(Image image) {

    if (Objects.isNull(image)) {
      return null;
    }

    ImageDTO imageDTO = new ImageDTO();

    imageDTO.setId(image.getId());
    imageDTO.setImgPath(image.getImgPath());

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    imageDTO.setUploadOn(format.format(image.getUploadOn()));

    return imageDTO;

  }

}
