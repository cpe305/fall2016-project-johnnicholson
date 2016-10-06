package dao;

import hibernate.HibernateUtil;
import model.Person;

public class PersonDAO extends GenericHibernateDAO<Person> {

  public Person findByEmail(String email) {
    return (Person) HibernateUtil.getFactory().getCurrentSession()
        .createQuery("from Person where email = :email").setString("email", email.toLowerCase())
        .uniqueResult();
  }

}
