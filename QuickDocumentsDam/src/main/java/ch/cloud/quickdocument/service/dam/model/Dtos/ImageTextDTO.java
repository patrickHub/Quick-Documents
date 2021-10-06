package ch.cloud.quickdocument.service.dam.model.Dtos;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

public class ImageTextDTO {


  private int id;

  private String content;


  private String imgPath;


  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }


  public String getContent() {
    return content;
  }


  public void setContent(String content) {
    this.content = content;
  }


  public String getImgPath() {
    return imgPath;
  }


  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }


}
