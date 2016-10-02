package app;
import javax.annotation.PreDestroy;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import dao.HibernateDAOFactory;
import model.Person;

@SpringBootApplication
public class Application {
  public static SessionFactory factory;
  public static HibernateDAOFactory daoFact;
  public static void main(String[] args) {
    factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Person.class).buildSessionFactory();
    daoFact = new HibernateDAOFactory();
    ApplicationContext ctx = SpringApplication.run(Application.class, args);

    
  }
  
  @PreDestroy
  public static void shutdown() {
    System.out.println("Shutdown Server");
    factory.close();
  }
}
