package Person;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import app.AuthInterceptor;
import controller.PersonController;
import controller.SessionController;
import controller.SessionController.Login;
import hibernate.HibernateUtil;
import model.Person;

public class StudentTest {

  private static People people = new People();

  AuthInterceptor auth;
  MockHttpServletResponse res;
  MockHttpServletRequest req;

  @Before
  public void setup() {
    people = new People();

    HibernateUtil.getFactory().getCurrentSession().beginTransaction();
    HibernateUtil.getFactory().getCurrentSession().createSQLQuery("delete from PrintRequest")
        .executeUpdate();
    HibernateUtil.getFactory().getCurrentSession().createSQLQuery("delete from PrintLocation")
        .executeUpdate();
    HibernateUtil.getFactory().getCurrentSession().createSQLQuery("delete from Person")
        .executeUpdate();
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(people.prsA);
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(people.prsB);
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(people.prsC);
    HibernateUtil.getFactory().getCurrentSession().getTransaction().commit();

    auth = new AuthInterceptor();
    res = new MockHttpServletResponse();
    req = new MockHttpServletRequest();

    req.setServletPath("/snss");
    req.setMethod("POST");
    SessionController.postSession(new Login(people.prsB.getEmail(), people.passB), req, res);

    req.setCookies(res.getCookies());
    assertTrue(auth.preHandle(req, res, null));
  }

  @Test
  public void addAdminAsStudent() {
    PersonController.postPerson(people.prsD, req, res);
    assertEquals(HttpStatus.BAD_REQUEST.value(), res.getStatus());
  }

  @Test
  public void addAdminAsAdmin() {
    req.setServletPath("/snss");
    req.setMethod("POST");
    SessionController.postSession(new Login(people.prsA.getEmail(), people.passA), req, res);

    req.setCookies(res.getCookies());
    assertTrue(auth.preHandle(req, res, null));

    PersonController.postPerson(people.prsD, req, res);
    assertEquals(HttpStatus.OK.value(), res.getStatus());
  }

  @Test
  public void deleteTest() {
    // TODO delete Test
  }

  @Test
  public void putTest() {
    Person mod = new Person();
    mod.setEmail(null);
    mod.setFirstName("NewFirstName");
    mod.setLastName("NewLastName");
    mod.setPhoneNumber("new PhoneNumber");
    // Should fail because email is changed
    PersonController.putPerson(people.prsD, people.prsB.getId(), req, res);
    assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
    
    PersonController.putPerson(mod, people.prsB.getId(), req, res);
    assertEquals(HttpStatus.OK.value(), res.getStatus());
    Person get = PersonController.getPerson(people.prsB.getId(), req, res);
    assertEquals(mod.getFirstName(), get.getFirstName());
    assertEquals(mod.getLastName(), get.getLastName());
    assertEquals(mod.getPhoneNumber(), get.getPhoneNumber());

    
  }

  @Test
  public void cannotGetAll() {
    assertNull(PersonController.getAllPeople(req, res));
    assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
  }

  @Test
  public void cannotGetOther() {
    Person person = PersonController.getPerson(people.prsC.getId(), req, res);
    assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
    assertNull(person);
  }

}
