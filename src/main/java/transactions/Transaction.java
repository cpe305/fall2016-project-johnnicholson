package transactions;

import model.Person;

import static hibernate.HibernateUtil.getFactory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.hibernate.JDBCException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public abstract class Transaction<T> {

  public static final int MAX_RETRIES = 10;

  protected HttpStatus responseCode = HttpStatus.OK;

  public HttpStatus getResponseCode() {
    return responseCode;
  }

  private HttpServletRequest req;
  private HttpServletResponse res;

  protected List<String> errors = new ArrayList<String>();

  public List<String> getErrors() {
    return errors;
  }

  protected app.Session getSession() {
    return (app.Session) req.getAttribute(app.Session.ATTRIBUTE_NAME);
  }

  protected boolean isAdmin() {
    return getSession() != null && getSession().role == Person.Role.Admin;
  }

  protected boolean isAdminOrUser(int userId) {
    return getSession().role == Person.Role.Admin || getSession().prsId == userId;
  }
  
  protected boolean isStaffOrUser(int userId) {
    return isAdminOrUser(userId) || getSession().role == Person.Role.Staff;
  }

  private void setResponse(boolean done) {
    if (!done) {
      res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    } else {
      res.setStatus(responseCode.value());
    }
    // TODO maybe use this to send detailed errors other than, it failed
    // res.sendError(HttpStatus.BAD_REQUEST.value(), new ObjectMapper().writeValueAsString(errors));
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
      } catch (JDBCException ex) {
        ex.printStackTrace();
        session.getTransaction().rollback();
        counter--;
      }
    }
    setResponse(done);
    return val;
  }

}
