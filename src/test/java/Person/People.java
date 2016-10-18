package Person;

import model.Person;
import model.Person.Role;

public class People {

  public String passA = "password";
  public Person prsA =
      new Person("admin", "admin", "admin@11test.edu", "805", Role.Admin, passA);
  public String passB = "passB";

  public Person prsB =
      new Person("student", "student", "student@11test.edu", "805", Role.Student, passB);
  public String passC = "passC";

  public Person prsC =
      new Person("student", "student", "student2@11test.edu", "805", Role.Student, passC);
  
  public String passD = "passD";
  public Person prsD =
      new Person("admin2", "admin2", "admin2@11test.edu", "805", Role.Admin, passD);

}
