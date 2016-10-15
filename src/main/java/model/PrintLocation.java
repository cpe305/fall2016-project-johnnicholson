package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderColumn;

@Entity
public class PrintLocation {

  private Integer id;
  private String name;
  private String description;
  private List<PrintRequest> queue;
  
  @Id
  @GeneratedValue
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  
  @OrderColumn(name="sequence")
  public List<PrintRequest> getQueue() {
    return queue;
  }
  public void setQueue(List<PrintRequest> queue) {
    this.queue = queue;
  }
  
  
}
