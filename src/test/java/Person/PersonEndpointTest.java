package Person;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hibernate.HibernateUtil;
import model.Person;
import model.Person.Role;

public class PersonEndpointTest {

  @Before
  public void setup() {
    HibernateUtil.getFactory().getCurrentSession().beginTransaction();
    HibernateUtil.getFactory().getCurrentSession().createSQLQuery("delete from Person").executeUpdate();
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(People.prsA.person);
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(People.prsB.person);
    HibernateUtil.getFactory().getCurrentSession().getTransaction().commit();
  }
  
  
  @Test
  public void getTest() {
    
  }
  
  public enum People {
    prsA(new Person("admin", "admin", "admin@11test.edu", "805", Role.Admin, "password")), 
    prsB(new Person("student", "student", "student@11test.edu", "805", Role.Student, "password")),
    prsC(new Person("student", "student", "student2@11test.edu", "805", Role.Student, "password"));

    public Person person;

    People(Person person) {
      this.person = person;
    }
  }
  
}
