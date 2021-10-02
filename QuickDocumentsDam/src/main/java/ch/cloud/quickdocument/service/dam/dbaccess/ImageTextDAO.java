package ch.cloud.quickdocument.service.dam.dbaccess;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ch.cloud.quickdocument.service.dam.model.entities.ImageText;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
@Repository
public class ImageTextDAO {


  @Autowired
  private SessionFactory sessionFactory;



  public int saveImageText(ImageText imageText) {

    Session session = sessionFactory.getCurrentSession();

    return (int) session.save(imageText);

  }


}
