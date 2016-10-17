package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.Person;
import model.PrintRequest;
import transactions.PersonTransactions.ChangePassword;
import transactions.PersonTransactions.GetAllPeople;
import transactions.PersonTransactions.GetPerson;
import transactions.PersonTransactions.GetRequests;
import transactions.PersonTransactions.PostPerson;
import transactions.PersonTransactions.PostRequest;
import transactions.PersonTransactions.PutPerson;

@RestController
@RequestMapping(value = "/api/prss")
public class PersonController {

  public static class PasswordChange {
    public String newPassword;
    public String oldPassword;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public static List<Person> getAllPeople(HttpServletRequest req, HttpServletResponse res) {
    List<Person> prss = new GetAllPeople().run(req, res);

    return prss;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public static void postPerson(@RequestBody Person person, HttpServletRequest req,
      HttpServletResponse res) {
    Integer prsId = new PostPerson(person).run(req, res);
    res.setHeader("Location", "prss/" + prsId);
    return;
  }

  @RequestMapping(value = "/{PrsId}", method = RequestMethod.PUT)
  public static void putPerson(@Valid @RequestBody Person person,
      @PathVariable(value = "PrsId") int prsId, HttpServletRequest req, HttpServletResponse res) {
    new PutPerson(person, prsId).run(req, res);
    return;
  }

  @RequestMapping(value = "/{PrsId}", method = RequestMethod.GET)
  public static Person getPerson(@PathVariable(value = "PrsId") int prsId, HttpServletRequest req,
      HttpServletResponse res) {
    Person p = new GetPerson(prsId).run(req, res);
    return p;
  }

  @RequestMapping(value = "/{PrsId}/password", method = RequestMethod.PUT)
  public static Integer changePassword(@PathVariable(value = "PrsId") int prsId,
      @RequestBody PasswordChange pwChange, HttpServletRequest req, HttpServletResponse res) {
    return new ChangePassword(pwChange, prsId).run(req, res);
  }

  @RequestMapping(value = "/{PrsId}/reqs", method = RequestMethod.POST)
  public static void createRequest(@PathVariable(value = "PrsId") int prsId,
      @RequestBody PrintRequest prtreq, HttpServletRequest req, HttpServletResponse res) {
    Integer reqId = new PostRequest(prsId, prtreq).run(req, res);
    res.setHeader("Location", "reqs/" + reqId);
    return;
  }

  @RequestMapping(value = "/{PrsId}/reqs", method = RequestMethod.POST)
  public static List<PrintRequest> getRequests(@PathVariable(value = "PrsId") int prsId,
      HttpServletRequest req, HttpServletResponse res) {
    return new GetRequests(prsId).run(req, res);
  }

}
