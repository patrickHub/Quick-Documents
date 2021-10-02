package ch.cloud.quickdocument.service.dam.model.entities;


import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */



@Entity
@Table(name = "Image")
public class Image {


  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @Column(name = "UploadOn")
  private Date uploadOn;


  @NotNull
  @Column(name = "ImgPath")
  private String imgPath;

  @OneToOne(mappedBy = "image")
  private ImageText imageText;


  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }


  public Date getUploadOn() {
    return uploadOn;
  }


  public void setUploadOn(Date uploadOn) {
    this.uploadOn = uploadOn;
  }


  public String getImgPath() {
    return imgPath;
  }


  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }


  public ImageText getImageText() {
    return imageText;
  }


  public void setImageText(ImageText imageText) {
    this.imageText = imageText;
  }



}
