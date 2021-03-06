package dao;

import java.util.List;

import org.apache.log4j.Logger;

import app.InvalidArgumentException;
import model.Person;
import model.PrintLocation;
import model.PrintRequest;

public class PrintRequestDAO extends GenericHibernateDAO<PrintRequest> {
  private static Logger lgr = Logger.getLogger(PrintRequestDAO.class);


  public void resequence(PrintRequest req, int newpos) throws InvalidArgumentException {
    Integer last = findLastPosition(req);
    
    if (newpos < 1 || newpos > last)
      throw new InvalidArgumentException();
    
    if (req.getSequence() < newpos) {
      getSession()
          .createSQLQuery("update PrintRequest set sequence = sequence - 1 where sequence <= "
              + newpos + " and sequence > " + req.getSequence() + " and location_id = "
              + req.getLocation().getId())
          .executeUpdate();
    } else if (req.getSequence() > newpos) {
      getSession()
          .createSQLQuery("update PrintRequest set sequence = sequence + 1 where sequence >= "
              + newpos + " and sequence < " + req.getSequence() + " and location_id = "
              + req.getLocation().getId())
          .executeUpdate();
    }
    getSession()
        .createSQLQuery(
            "update PrintRequest set sequence = " + newpos + " where id = " + req.getId())
        .executeUpdate();
  }

  public Integer findLastPosition(PrintRequest req) {
    return (Integer) getSession()
        .createSQLQuery("select max(sequence) from PrintRequest preq where preq.location_id = "
            + req.getLocation().getId() + " group by location_id;")
        .uniqueResult();
  }
  
  public List<PrintRequest> findByPerson(Integer id) {
    return (List<PrintRequest>) getSession().createSQLQuery("select * from PrintRequest where owner_id = " + id + ";").list();
  }
  
  public List<PrintRequest> findByPersonAndLocation(Person owner, PrintLocation location) {
    return (List<PrintRequest>) getSession().createQuery("from PrintRequest where owner = :owner and location = :location").setParameter("owner", owner).setParameter("location", location).list();
  }
  
  public void moveToEnd(PrintRequest req) {
    Integer last = findLastPosition(req);
    try {
      resequence(req, last);
    } catch (InvalidArgumentException e) {
      lgr.error(e);
    }
  }

}
