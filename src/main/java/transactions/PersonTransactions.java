package transactions;

import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;

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
      if (null == prsDAO.findByEmail(prs.getEmail())) {
        prsDAO.makePersistent(prs);
      }
      else {
        responseCode = Status.BAD_REQUEST;
        return null;
      }
      return prs.getId();
    }
    
  }
  
  public static class PutPerson extends Transaction<Integer> {
	    private Person prs;
	    private Integer id;
	    
	    public PutPerson(Person prs, Integer id) {
	      this.prs = prs;
	      this.id = id;
	    }
	    
	    @Override
	    public Integer action() {
	      PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
	      Person dbprs = prsDAO.findById(id);
	      if (isAdmin()) {
	        BeanUtils.copyProperties(prs, dbprs, "id");
	      }
	      else if (!dbprs.getEmail().equals(prs.getEmail()) || dbprs.getRole() != prs.getRole()){
	        this.responseCode = Status.UNAUTHORIZED;
	        return null;
	      }
	      dbprs.setFirstName(prs.getFirstName());
	      dbprs.setLastName(prs.getLastName());
	      dbprs.setPhoneNumber(prs.getPhoneNumber());
	      return null;
	      
	    }
	    
	  }
}
