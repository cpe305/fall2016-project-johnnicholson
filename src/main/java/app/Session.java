package app;

import model.Person;
import model.Person.Role;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;


public class Session {

  public static final int SESSION_TIME = 3600 * 1000 * 7;
  public static final String COOKIE_NAME = "USERID";
  public static final String ATTRIBUTE_NAME = "SESSION_INFO";

  private Session(Integer prsId, Role role) {
    this.prsId = prsId;
    this.role = role;
    dateCreated = new Date();
  }

  private static SecureRandom random = new SecureRandom();
  private static HashMap<String, Session> sessions = new HashMap<String, Session>();

  public static Session getSession(String id) {
    return sessions.get(id);
  }

  public static String addSession(Person p) {
    Session s = new Session(p.getId(), p.getRole());
    sessions.put(s.createId(), s);
    return s.id;
  }

  public static void deleteSession(String id) {
    sessions.remove(id);
  }

  public String id;
  public Integer prsId;
  public Role role;
  public Date dateCreated;

  public String createId() {
    this.id = new BigInteger(260, random).toString(32);
    return this.id;
  }


}
