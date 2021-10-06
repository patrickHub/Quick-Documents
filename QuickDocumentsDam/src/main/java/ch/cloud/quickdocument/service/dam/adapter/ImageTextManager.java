package ch.cloud.quickdocument.service.dam.adapter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;
import ch.cloud.quickdocument.service.dam.dbaccess.ImageTextDAO;
import ch.cloud.quickdocument.service.dam.model.Dtos.ImageTextDTO;
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
  private ImageTextDAO imageTextDAO;


  @Transactional
  public int saveImageText(ImageTextDTO imageTextDTO) {

    ImageText imageText = DtoToDbMapper.mapImageTextDtoToDb(imageTextDTO);

    return imageTextDAO.saveImageText(imageText);

  }


  @Transactional
  public List<ImageTextDTO> searchImageByText(String text) {

    List<ImageTextDTO> results = new ArrayList<>();

    for (ImageText imageText : imageTextDAO.searchImagesBytext(text)) {

      results.add(DbToDtoMapper.mapImageTextToDto(imageText));
    }

    return results;

  }



}
