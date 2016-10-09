package transactions;

import org.hibernate.Hibernate;

import java.util.List;

import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.Person;

public class PersonTransactions {
  
  public static class GetAllPeople extends Transaction<List<Person>> {
    
    @Override
    public List<Person> action() {
      if (isAdmin()) {
        PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
        List<Person> prss = prsDAO.findAll();
        return prss;
      }
      else {
        responseCode = Status.UNAUTHORIZED;
        return null;
      }
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
      System.out.println(p);
      if (p != null)
        Hibernate.initialize(p);
      else { 
        if (isAdmin())
          responseCode = Status.NOT_FOUND;
        else
          responseCode = Status.UNAUTHORIZED;
      }
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
