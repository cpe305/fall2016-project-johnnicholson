package app;

import model.Person.Role;

import java.util.ArrayList;
import java.util.Date;


public class Session {

  public static final int SESSION_TIME = 3600 * 1000 * 7;

  public Session(){
    dateCreated = new Date();
  }
  
  public static ArrayList<Session> currentSession = new ArrayList<Session>();
  
  public String id;
  public Integer userId;
  public Role role;
  public Date dateCreated;
  
  public String createId() {
    this.id = "random String"; // 
    return this.id;
  }
 
  
}
