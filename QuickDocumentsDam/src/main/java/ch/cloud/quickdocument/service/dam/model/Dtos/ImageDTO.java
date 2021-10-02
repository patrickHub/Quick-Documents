package ch.cloud.quickdocument.service.dam.model.Dtos;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

public class ImageDTO {


  private int id;

  private String uploadOn;


  private String imgPath;


  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }


  public String getUploadOn() {
    return uploadOn;
  }


  public void setUploadOn(String uploadOn) {
    this.uploadOn = uploadOn;
  }


  public String getImgPath() {
    return imgPath;
  }


  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }



}
