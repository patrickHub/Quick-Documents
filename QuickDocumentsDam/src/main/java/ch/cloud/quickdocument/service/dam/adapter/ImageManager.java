package ch.cloud.quickdocument.service.dam.adapter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;
import ch.cloud.quickdocument.service.dam.dbaccess.ImageDAO;
import ch.cloud.quickdocument.service.dam.model.Dtos.ImageDTO;
import ch.cloud.quickdocument.service.dam.model.entities.Image;
import ch.cloud.quickdocument.service.dam.utilities.Utility;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

@Component
@RequestScope
public class ImageManager {


  @Autowired
  private ImageDAO imageDAO;


  @Transactional
  public int saveImage(ImageDTO imageDTO) {

    Image image = DtoToDbMapper.mapImageDtoToDb(imageDTO);

    image.setUploadOn(new Date(Utility.getCurrentInCustomFormat().getCalendar().getTime().getTime()));

    return imageDAO.saveImage(image);

  }

  @Transactional
  public List<ImageDTO> searchImageByText(String text) {

    List<ImageDTO> results = new ArrayList<>();

    for (Image image : imageDAO.searchImagesBytext(text)) {

      results.add(DbToDtoMapper.mapImageToDto(image));
    }

    return results;

  }


}
