package Print;

import Person.People;
import model.PrintRequest;

public class Requests {

  public PrintRequest reqA;
  public PrintRequest reqB;
  public PrintRequest reqC;
  
  public Requests(People people, Locations locs) {
    reqA = new PrintRequest(people.prsA, null, "test", 1, locs.locA);
    reqB = new PrintRequest(people.prsB, null, "test", 2, locs.locA);
    reqC = new PrintRequest(people.prsC, null, "test", 3, locs.locA);


  }

  
}
