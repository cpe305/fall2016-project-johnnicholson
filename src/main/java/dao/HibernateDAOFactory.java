package dao;

import hibernate.HibernateUtil;

import org.apache.log4j.Logger;

public class HibernateDAOFactory extends DAOFactory {
  Logger lgr = Logger.getLogger(HibernateDAOFactory.class);

  public PersonDAO getPersonDAO() {
    return (PersonDAO) instantiateDAO(PersonDAO.class);
  }

  public PrintLocationDAO getPrintLocationDAO() {
    return (PrintLocationDAO) instantiateDAO(PrintLocationDAO.class);
  }

  public PrintRequestDAO getPrintRequestDAO() {
    return (PrintRequestDAO) instantiateDAO(PrintRequestDAO.class);
  }


  private GenericHibernateDAO instantiateDAO(Class daoClass) {
    try {
      GenericHibernateDAO dao = (GenericHibernateDAO) daoClass.newInstance();
      dao.setSession(HibernateUtil.getFactory().getCurrentSession());
      return dao;
    } catch (Exception ex) {
      lgr.error(ex);
      throw new IllegalArgumentException();
    }
  }

}
