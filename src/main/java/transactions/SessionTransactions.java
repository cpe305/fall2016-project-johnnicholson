package transactions;

import app.Session;
import hibernate.HibernateUtil;
import model.Person;

public class SessionTransactions {
  
  public static class PostSession extends Transaction<String> {
    private String email;
    private String password;

    public PostSession(String email, String password) {
      this.email = email;
      this.password = password;
    }

    public String action() {
      Person p = HibernateUtil.getDAOFact().getPersonDAO().findByEmail(email);
      if (p != null && p.checkPassword(password)) {
        return Session.addSession(p);
      }
      else {
        this.responseCode = Status.UNAUTHORIZED;
      }
      return null;
    }
  }
  
  public static class GetSession extends Transaction<Session> {
    
    public GetSession() {

    }
    
    public Session action() {
      Session s = getSession();
      if (s != null)
        return s;
      this.responseCode = Status.NOT_FOUND;
      return null;
    }
  }

}
