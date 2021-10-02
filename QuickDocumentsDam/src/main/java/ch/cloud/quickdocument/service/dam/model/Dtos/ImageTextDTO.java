package ch.cloud.quickdocument.service.dam.model.Dtos;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

public class ImageTextDTO {


  private int id;

  private String content;


  private ImageDTO imageDTO;


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


  public ImageDTO getImageDTO() {
    return imageDTO;
  }


  public void setImageDTO(ImageDTO imageDTO) {
    this.imageDTO = imageDTO;
  }

}
