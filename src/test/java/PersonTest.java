import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import app.PersonController;
import app.Util;
import model.Person;
import model.Person.Role;

public class PersonTest {

  @Test
  public void createAdmin() {
    MockHttpServletResponse response = new MockHttpServletResponse();
    PersonController.postPerson(People.prsA.person, response);
    
    Person b = PersonController.getPerson(Util.getFinalId((String)response.getHeader("Location")));
    assertEquals(People.prsA.person, b);
  }
  
  public enum People {
      prsA(new Person("a", "a", "aasd@calpoly.edu", "805", Role.Admin, "a"));
      
      public  Person person;
      People(Person person) {
        this.person = person;
      }
  }
}
