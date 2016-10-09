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

}
