package ch.cloud.quickdocument.service.dam.dbaccess;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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


  public List<ImageText> searchImagesBytext(String text) {

    Session session = sessionFactory.getCurrentSession();

    String queryString = "SELECT Id, content, ImgPath, MATCH(Content) AGAINST(:text) AS score\r\n" + "FROM ImageText\r\n"
        + "WHERE MATCH(Content) AGAINST(:text) > 0 ORDER BY score DESC;";

    Query<ImageText> query = session.createNativeQuery(queryString, ImageText.class);

    query.setParameter("text", text);


    return query.getResultList();

  }


}
