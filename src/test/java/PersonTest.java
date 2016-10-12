import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import app.AuthInterceptor;
import app.Util;
import controller.PersonController;
import controller.SessionController;
import controller.SessionController.Login;
import hibernate.HibernateUtil;
import model.Person;
import model.Person.Role;
import transactions.Transaction;


public class PersonTest {

  private AuthInterceptor auth;
  MockHttpServletResponse res;
  MockHttpServletRequest req;



  @BeforeClass
  public static void injectAdmin() {
    HibernateUtil.getFactory().getCurrentSession().beginTransaction();
    HibernateUtil.getFactory().getCurrentSession().createSQLQuery("delete from Person").executeUpdate();
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(People.prsA.person);
    HibernateUtil.getFactory().getCurrentSession().getTransaction().commit();;

  }

  @Before
  public void setup() {
    auth = new AuthInterceptor();
    res = new MockHttpServletResponse();
    req = new MockHttpServletRequest();
  }

  @Test
  public void createStudent() {
    PersonController.postPerson(People.prsB.person, req, res);

    Person b = PersonController.getPerson(Util.getFinalId((String) res.getHeader("Location")),
        req, res);
    assertEquals(People.prsB.person, b);
  }

  @Test
  public void permissionTest() {

    req.setServletPath("/prss");
    req.setMethod("POST");
    assertTrue(auth.preHandle(req, res, null));
    PersonController.postPerson(People.prsC.person, req, res);
    int prsId = Util.getFinalId((String) res.getHeader("Location"));
    assertEquals(res.getStatus(), Transaction.Status.OK.getValue());
    
    req.setServletPath("/snss");
    req.setMethod("POST");
    SessionController.postSession(new Login(People.prsC.person.getEmail(), "password"), req, res);
    
    req.setCookies(res.getCookies());
    Person person;
    
    req.setServletPath("/prss");
    req.setMethod("GET");
    assertTrue(auth.preHandle(req, res, null));
    List<Person> people = PersonController.getAllPeople(req, res);
    assertEquals(res.getStatus(), Transaction.Status.UNAUTHORIZED.getValue());
    assertNull(people);
    
    req.setServletPath("/prss/24352345");
    req.setMethod("GET");
    person = PersonController.getPerson(24352345, req, res);
    assertEquals(res.getStatus(), Transaction.Status.UNAUTHORIZED.getValue());
    assertNull(person);

    req.setServletPath("/prss/" + People.prsC.person.getId());
    req.setMethod("GET");
    Person p = PersonController.getPerson(prsId, req, res);
    assertEquals(res.getStatus(), Transaction.Status.OK.getValue());
    assertEquals(People.prsC.person, p);

    req.setServletPath("/prss");
    req.setMethod("PUT");
    assertTrue(auth.preHandle(req, res, null));
    People.prsC.person.setEmail("newEmail@test.com");
    PersonController.putPerson(People.prsC.person, prsId, req, res);
    assertEquals(res.getStatus(), Transaction.Status.UNAUTHORIZED.getValue());
    
    
    p = PersonController.getPerson(prsId, req, res);
    assertNotEquals(People.prsC.person.getEmail(), p.getEmail());
    
    req.setServletPath("/prss");
    req.setMethod("PUT");
    assertTrue(auth.preHandle(req, res, null));
    People.prsC.person.setEmail(p.getEmail());
    People.prsC.person.setFirstName("newName");
    PersonController.putPerson(People.prsC.person, prsId, req, res);
    assertEquals(res.getStatus(), Transaction.Status.OK.getValue());
    
    p = PersonController.getPerson(prsId, req, res);
    assertEquals(People.prsC.person.getFirstName(), p.getFirstName());

    
  }

  public enum People {
    prsA(new Person("admin", "admin", "admin@11test.edu", "805", Role.Admin, "password")), prsB(
        new Person("student", "student", "student@11test.edu", "805", Role.Student,
            "password")), prsC(
                new Person("student", "student", "student2@11test.edu", "805", Role.Student,
                    "password"));

    public Person person;

    People(Person person) {
      this.person = person;
    }
  }
}
