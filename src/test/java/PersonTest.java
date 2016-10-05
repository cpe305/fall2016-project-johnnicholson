import app.PersonController;
import app.Util;
import hibernate.HibernateUtil;
import model.Person;
import model.Person.Role;

import org.springframework.mock.web.MockHttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest {

  @Before
  public void injectAdmin() {
    HibernateUtil.getFactory().getCurrentSession().beginTransaction();
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(People.prsA.person);
    HibernateUtil.getFactory().getCurrentSession().getTransaction().commit();;

  }

  @Test
  public void createAdmin() {
    MockHttpServletResponse response = new MockHttpServletResponse();
    PersonController.postPerson(People.prsB.person, response);

    Person b = PersonController.getPerson(Util.getFinalId((String) response.getHeader("Location")));
    assertEquals(People.prsB.person, b);
  }

  public enum People {
    prsA(new Person("admin", "admin", "admin@11test.edu", "805", Role.Admin, "password")),
    prsB(new Person("student", "student", "student@11test.edu", "805", Role.Student, "password"));

    public Person person;

    People(Person person) {
      this.person = person;
    }
  }
}
