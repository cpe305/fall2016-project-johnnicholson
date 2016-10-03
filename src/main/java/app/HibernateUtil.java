package app;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dao.DAOFactory;
import dao.HibernateDAOFactory;
import model.Person;

public class HibernateUtil {
  private static SessionFactory factory;
  private static DAOFactory daoFact = new HibernateDAOFactory();

  public static SessionFactory getFactory() {
    if (factory != null) {
      return factory;
    }
    return factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Person.class).buildSessionFactory();
  }
  
  public static DAOFactory getDAOFact() {
    return daoFact;
  }

}
