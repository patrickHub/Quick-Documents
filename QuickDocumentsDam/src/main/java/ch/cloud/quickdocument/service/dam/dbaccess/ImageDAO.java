package ch.cloud.quickdocument.service.dam.dbaccess;

import org.springframework.stereotype.Repository;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */


@Repository
public class ImageDAO {


  // @Autowired
  // private SessionFactory sessionFactory;
  //
  //
  //
  // public int saveImage(Image image) {
  //
  // Session session = sessionFactory.getCurrentSession();
  //
  // return (int) session.save(image);
  //
  // }
  //
  // public Image getImageById(int id) {
  //
  // Session session = sessionFactory.getCurrentSession();
  //
  // return session.find(Image.class, id);
  //
  // }
  //
  //
  // public List<Image> searchImagesBytext(String text) {
  //
  // Session session = sessionFactory.getCurrentSession();
  //
  // String queryString = "SELECT Image.Id AS Id, ImgPath, UploadOn, MATCH(Content) AGAINST(:text) AS score\r\n"
  // + "FROM ImageText INNER JOIN Image ON ImageText.ImageId=Image.Id\r\n" + "WHERE MATCH(Content) AGAINST(:text) > 0 ORDER BY score DESC;";
  //
  // Query<Image> query = session.createNativeQuery(queryString, Image.class);
  //
  // query.setParameter("text", text);
  //
  //
  // return query.getResultList();
  //
  // }


}
