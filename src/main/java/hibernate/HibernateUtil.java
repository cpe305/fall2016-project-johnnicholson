package hibernate;

import dao.DAOFactory;
import dao.HibernateDAOFactory;
import model.Person;
import model.PrintLocation;
import model.PrintRequest;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
  // Eager initialization of singletons
  private static SessionFactory factory = initFactory();
  private static DAOFactory daoFact = new HibernateDAOFactory();

  public static SessionFactory getFactory() {
    return factory;
  }

  public static DAOFactory getDAOFact() {
    return daoFact;
  }
  
  private static SessionFactory initFactory() {
    Configuration configuration = new Configuration();
    configuration.configure("hibernate.cfg.xml");
    configuration.addAnnotatedClass(Person.class);
    configuration.addAnnotatedClass(PrintLocation.class);
    configuration.addAnnotatedClass(PrintRequest.class);
    ServiceRegistry serviceRegistry =
        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    return configuration.buildSessionFactory(serviceRegistry);
  }

}
