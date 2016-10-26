package transactions;

import app.Session;
import hibernate.HibernateUtil;
import model.Person;

import org.springframework.http.HttpStatus;

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
      } else {
        this.responseCode = HttpStatus.UNAUTHORIZED;
      }
      return null;
    }
  }

  public static class GetSession extends Transaction<Session> {

    public GetSession() {

    }

    public Session action() {
      Session s = getSession();
      if (s == null)
        this.responseCode = HttpStatus.NOT_FOUND;
      return s;
    }
  }

  public static class DeleteSession extends Transaction<Integer> {

    public DeleteSession() {

    }

    public Integer action() {
      Session s = getSession();
      if (s == null) {
        this.responseCode = HttpStatus.NOT_FOUND;
      } else {
        Session.deleteSession(s.id);
      }
      return null;
    }
  }
}
