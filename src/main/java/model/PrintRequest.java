package model;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import api.PrintRequestPost;

@Entity
public class PrintRequest {

  public PrintRequest() {

  }
  
  public PrintRequest(PrintRequestPost preqPost, Person owner, PrintLocation location) {
    this.owner = owner;
    this.location = location;
    this.file = preqPost.file;
    this.fileName = preqPost.fileName;
    this.description = preqPost.description;
    this.createdAt = Timestamp.from(Instant.now());
  }


  public PrintRequest(Person owner, byte[] file, String fileName, Integer sequence,
      PrintLocation location) {
    super();
    this.owner = owner;
    this.file = file;
    this.fileName = fileName;
    this.sequence = sequence;
    this.location = location;
    this.createdAt = Timestamp.from(Instant.now());
  }


  private Integer id;
  private Person owner;
  private byte[] file;
  private String fileName;
  private Integer sequence;
  private PrintLocation location;
  private Timestamp createdAt;
  private String description;

  @Id
  @GeneratedValue
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @ManyToOne
  public Person getOwner() {
    return owner;
  }

  public void setOwner(Person owner) {
    this.owner = owner;
  }
  @Lob
  @Column(length = 20971520)
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

  @ManyToOne
  public PrintLocation getLocation() {
    return location;
  }

  public void setLocation(PrintLocation location) {
    this.location = location;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
