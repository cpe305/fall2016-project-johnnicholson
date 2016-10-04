package app;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Transactions.PersonTransactions.GetPerson;
import Transactions.PersonTransactions.PostPerson;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.Person;

@RestController
public class PersonController {
  
  @RequestMapping(value = "/prss", method = RequestMethod.GET)
  public static List<Person> getAllPeople() {
    HibernateUtil.getFactory().getCurrentSession().beginTransaction();
    PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
    List<Person> prss = prsDAO.findAll();
    HibernateUtil.getFactory().getCurrentSession().getTransaction().commit();
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
    return p;
  }
  
}
