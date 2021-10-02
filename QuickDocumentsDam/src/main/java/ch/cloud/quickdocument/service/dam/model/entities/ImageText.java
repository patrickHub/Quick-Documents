package ch.cloud.quickdocument.service.dam.model.entities;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

@Entity
@Table(name = "ImageText")
public class ImageText {


  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @Column(name = "Content")
  private String content;


  @NotNull
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ImageId", referencedColumnName = "Id")
  private Image image;


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


  public Image getImage() {
    return image;
  }


  public void setImage(Image image) {
    this.image = image;
  }



}
