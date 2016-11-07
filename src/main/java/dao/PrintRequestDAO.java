package dao;

import model.PrintRequest;

public class PrintRequestDAO extends GenericHibernateDAO<PrintRequest> {


  // TODO think this one out
  public void resequence(PrintRequest req, int newpos) {

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
              + req.getLocation().getId() + ";" + "update PrintRequest set sequence = " + newpos
              + " where id = " + req.getId())
          .executeUpdate();
    }
    getSession().createSQLQuery(
        "update PrintRequest set sequence = " + newpos + " where id = " + req.getId());
  }

  public void moveToEnd(PrintRequest req) {
    Integer last = (Integer) getSession()
        .createSQLQuery("select max(sequence) from PrintRequest preq where preq.location_id = "
            + req.getLocation().getId() + " group by location_id;")
        .uniqueResult();
    resequence(req, last);
  }

}
