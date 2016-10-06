package Transactions;

import org.hibernate.Hibernate;

import java.util.List;

import dao.PersonDAO;
import hibernate.HibernateUtil;
import hibernate.Transaction;
import model.Person;

public class PersonTransactions {
  
  public static class GetAllPeople extends Transaction<List<Person>> {
    
    @Override
    public List<Person> action() {
      PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
      List<Person> prss = prsDAO.findAll();
      return prss;
    }
  }
    
  
  public static class GetPerson extends Transaction<Person> {
    private int prsId;
    
    public GetPerson(int prsId) {
      this.prsId = prsId;
    }
    
    @Override
    public Person action() {
      Person p = HibernateUtil.getDAOFact().getPersonDAO().findById(prsId);
      Hibernate.initialize(p);
      return p;
    }
    
  }
  
  public static class PostPerson extends Transaction<Integer> {
    private Person prs;
    
    public PostPerson(Person prs) {
      this.prs = prs;
    }
    
    @Override
    public Integer action() {
      PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
      prsDAO.makePersistent(prs);
      return prs.getId();
    }
    
  }
}
