package hibernate;

import dao.DAOFactory;
import dao.HibernateDAOFactory;
import model.Person;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
  private static SessionFactory factory;
  private static DAOFactory daoFact = new HibernateDAOFactory();

  public static SessionFactory getFactory() {
    if (factory != null) {
      return factory;
    }
    Configuration configuration = new Configuration();
    configuration.configure("hibernate.cfg.xml");
    configuration.addAnnotatedClass(Person.class);
    ServiceRegistry serviceRegistry =
        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    factory = configuration.buildSessionFactory(serviceRegistry);
    return factory;
  }

  public static DAOFactory getDAOFact() {
    return daoFact;
  }

}
