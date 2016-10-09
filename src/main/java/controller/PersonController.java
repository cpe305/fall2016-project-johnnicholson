package controller;

import model.Person;
import transactions.PersonTransactions.GetAllPeople;
import transactions.PersonTransactions.GetPerson;
import transactions.PersonTransactions.PostPerson;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class PersonController {
  
  @RequestMapping(value = "/prss", method = RequestMethod.GET)
  public static List<Person> getAllPeople() {
    List<Person> prss = new GetAllPeople().run();

    return prss;
  }
  
  @RequestMapping(value = "/prss", method = RequestMethod.POST)
  public static void postPerson(@Valid @RequestBody Person person, HttpServletResponse  response) {
    Integer prsId = new PostPerson(person).run();
    response.setHeader("Location", "prss/" + prsId);
    return;
  }
  
  @RequestMapping(value = "/prss/{PrsId}", method = RequestMethod.GET)
  public static Person getPerson(@PathVariable(value="PrsId") int prsId) {
    Person p = new GetPerson(prsId).run();
    System.out.println(p);
    return p;
  }
  
}
