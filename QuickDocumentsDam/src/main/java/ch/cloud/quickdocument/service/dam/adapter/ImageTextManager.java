package ch.cloud.quickdocument.service.dam.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;
import ch.cloud.quickdocument.service.dam.dbaccess.ImageDAO;
import ch.cloud.quickdocument.service.dam.dbaccess.ImageTextDAO;
import ch.cloud.quickdocument.service.dam.model.Dtos.ImageTextDTO;
import ch.cloud.quickdocument.service.dam.model.entities.Image;
import ch.cloud.quickdocument.service.dam.model.entities.ImageText;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

@Component
@RequestScope
public class ImageTextManager {


  @Autowired
  private ImageDAO imageDAO;

  @Autowired
  private ImageTextDAO imageTextDAO;


  @Transactional
  public int saveImageText(ImageTextDTO imageTextDTO) {

    ImageText imageText = DtoToDbMapper.mapImageTextDtoToDb(imageTextDTO);

    Image image = imageDAO.getImageById(imageTextDTO.getImageDTO().getId());

    imageText.setImage(image);

    return imageTextDAO.saveImageText(imageText);

  }


}
