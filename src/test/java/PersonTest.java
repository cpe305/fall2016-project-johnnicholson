import app.PersonController;
import model.Person;
import model.Person.Role;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest {

  @Test
  public void createAdmin() {
    int id = PersonController.postPerson(People.prsA.person);
    Person b = PersonController.getPerson(id);
    assertEquals(People.prsA.person, PersonController.getPerson(id));
  }
  
  public enum People {
      prsA(new Person("a", "a", "aasd@calpoly.edu", "805", Role.Admin, "a"));
      
      public  Person person;
      People(Person person) {
        this.person = person;
      }
  }
}
