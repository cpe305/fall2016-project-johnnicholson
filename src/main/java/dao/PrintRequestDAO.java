package dao;

import model.PrintRequest;

public class PrintRequestDAO extends GenericHibernateDAO<PrintRequest> {


  // TODO think this one out
  public void resequence(PrintRequest req, int newpos) {

    if (req.getSequence() < newpos) {
      getSession()
          .createSQLQuery("update PrintRequest set sequence = sequence - 1 where sequence <= "
              + newpos + " and sequence > " + req.getSequence() + ";"
              + "update PrintRequest set sequence = " + newpos + " where id = " + req.getId())
          .executeUpdate();
    } else if (req.getSequence() > newpos) {
      getSession()
          .createSQLQuery("update PrintRequest set sequence = sequence + 1 where sequence >= "
              + newpos + " and sequence < " + req.getSequence() + ";"
              + "update PrintRequest set sequence = " + newpos + " where id = " + req.getId())
          .executeUpdate();
    }
  }

}
