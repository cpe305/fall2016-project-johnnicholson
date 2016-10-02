package dao;

import org.hibernate.Session;

import app.Application;

public class HibernateDAOFactory extends DAOFactory {

  public PersonDAO getPersonDAO() {
    return (PersonDAO) instantiateDAO(PersonDAO.class);
  }


  private GenericHibernateDAO instantiateDAO(Class daoClass) {
    try {
      GenericHibernateDAO dao = (GenericHibernateDAO) daoClass.newInstance();
      dao.setSession(Application.factory.getCurrentSession());
      return dao;
    } catch (Exception ex) {
      throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
    }
  }

}
