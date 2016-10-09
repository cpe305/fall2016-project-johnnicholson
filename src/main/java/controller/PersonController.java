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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class PersonController {

  @RequestMapping(value = "/prss", method = RequestMethod.GET)
  public static List<Person> getAllPeople(HttpServletRequest req, HttpServletResponse res) {
    List<Person> prss = new GetAllPeople().run(req, res);

    return prss;
  }

  @RequestMapping(value = "/prss", method = RequestMethod.POST)
  public static void postPerson(@Valid @RequestBody Person person, HttpServletRequest req,
      HttpServletResponse res) {
    Integer prsId = new PostPerson(person).run(req, res);
    res.setHeader("Location", "prss/" + prsId);
    return;
  }

  @RequestMapping(value = "/prss/{PrsId}", method = RequestMethod.GET)
  public static Person getPerson(@PathVariable(value = "PrsId") int prsId, HttpServletRequest req,
      HttpServletResponse res) {
    Person p = new GetPerson(prsId).run(req, res);
    return p;
  }

}
