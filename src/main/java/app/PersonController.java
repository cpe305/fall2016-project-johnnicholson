package app;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.PersonDAO;
import model.Person;

@RestController
public class PersonController {

  @RequestMapping("/prss")
  public List<Person> index() {
    Application.factory.getCurrentSession().beginTransaction();
    PersonDAO prsDAO = Application.daoFact.getPersonDAO();
    List<Person> prss = prsDAO.findAll();
    Application.factory.getCurrentSession().getTransaction().commit();
    return prss;
  }
  
  @RequestMapping(value = "/prss/{PrsId}", method = RequestMethod.GET)
  public Person getPerson(@PathVariable(value="PrsId") int prsId) {
    Person p = null;
    return p;
  }
  
}
