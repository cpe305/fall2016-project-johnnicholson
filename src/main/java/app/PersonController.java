package app;

import dao.PersonDAO;
import model.Person;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.hibernate.Hibernate;

import java.util.List;

import javax.validation.Valid;

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
  public static int postPerson(@Valid @RequestBody Person person) {
    HibernateUtil.getFactory().getCurrentSession().beginTransaction();
    PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
    prsDAO.makePersistent(person);
    HibernateUtil.getFactory().getCurrentSession().getTransaction().commit();
    return person.getId();
  }
  
  @RequestMapping(value = "/prss/{PrsId}", method = RequestMethod.GET)
  public static Person getPerson(@PathVariable(value="PrsId") int prsId) {
    //TODO factor out transaction requests to pattern 
    HibernateUtil.getFactory().getCurrentSession().beginTransaction();
    Person p = HibernateUtil.getDAOFact().getPersonDAO().findById(prsId);
    Hibernate.initialize(p);
    HibernateUtil.getFactory().getCurrentSession().getTransaction().commit();
    return p;
  }
  
}
