package transactions;

import static hibernate.HibernateUtil.getFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Person;

@Component
public abstract class Transaction<T> {

  public enum Status {
    OK(200), BAD_REQUEST(400), UNAUTHORIZED(401), ERROR(500), NOT_FOUND(404);

    private final int value;

    Status(final int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  public static final int MAX_RETRIES = 10;

  protected Status responseCode = Status.OK;

  public Status getResponseCode() {
    return responseCode;
  }

  private HttpServletRequest req;
  private HttpServletResponse res;

  protected app.Session getSession() {
    return (app.Session) req.getAttribute(app.Session.ATTRIBUTE_NAME);
  }

  protected boolean isAdmin() {
    return getSession().role == Person.Role.Admin;
  }

  protected boolean isAdminOrUser(int userId) {
    return getSession().role == Person.Role.Admin || getSession().userId == userId;
  }

  private void setResponse(boolean done) {
    if (!done) {
      res.setStatus(Status.ERROR.getValue());
    } else {
      res.setStatus(responseCode.getValue());
    }
  }

  public abstract T action();

  public final T run(HttpServletRequest req, HttpServletResponse res) {
    this.res = res;
    this.req = req;
    Session session = getFactory().getCurrentSession();
    int counter = MAX_RETRIES;
    boolean done = false;
    T val = null;
    while (!done && counter > 0) {
      try {
        session.beginTransaction();
        val = action();
        session.getTransaction().commit();
        done = true;
      } catch (JDBCException e) {
        e.printStackTrace();
        session.getTransaction().rollback();
        counter--;
      }
    }
    setResponse(done);
    return val;
  }

}
