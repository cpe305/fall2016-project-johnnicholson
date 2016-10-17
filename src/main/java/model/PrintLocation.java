package model;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.hibernate.annotations.SortNatural;

@Entity
public class PrintLocation {

  public PrintLocation() {
    
  }

  public PrintLocation(String name, String description) {
    this.name = name;
    this.description = description;
    this.queue = new TreeMap<Integer,PrintRequest>();
  }

  private Integer id;
  private String name;
  private String description;
  private SortedMap<Integer,PrintRequest> queue;

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
  
  @OneToMany(cascade=CascadeType.ALL, mappedBy="location")
  @MapKey(name="sequence")
  @SortNatural
  public SortedMap<Integer,PrintRequest> getQueue() {
    return queue;
  }

  public void setQueue(SortedMap<Integer,PrintRequest> queue) {
    this.queue = queue;
  }
  
  public void addPrintRequest(PrintRequest req) {
    //TODO sequence stuff
    queue.put(queue.lastKey() + 1, req);
  }
  public void removePrintRequest(PrintRequest req) {
    queue.remove(req.getSequence());
  }


}
