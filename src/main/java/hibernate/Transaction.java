package hibernate;

import static hibernate.HibernateUtil.getFactory;

import org.hibernate.JDBCException;
import org.hibernate.Session;

public abstract class Transaction {
  public static final int MAX_RETRIES = 10;
  public abstract void action();

  public final void run() {
    Session session = getFactory().getCurrentSession();
    int counter = MAX_RETRIES;
    boolean done = false;
    while (!done && counter > 0) {
      try {
        session.beginTransaction();
        action();
        session.getTransaction().commit();
        done = true;
      } catch (JDBCException e) {
        e.printStackTrace();
      }
    }
  }

}
