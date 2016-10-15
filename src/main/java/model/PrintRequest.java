package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PrintRequest {

  private Integer id;
  private Person owner;
  private byte[] file;
  private String fileName;
  private Integer sequence;
  @Id
  @GeneratedValue
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Person getOwner() {
    return owner;
  }
  public void setOwner(Person owner) {
    this.owner = owner;
  }
  
  public byte[] getFile() {
    return file;
  }
  public void setFile(byte[] file) {
    this.file = file;
  }
  
  public String getFileName() {
    return fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public Integer getSequence() {
    return sequence;
  }
  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }
  
}
