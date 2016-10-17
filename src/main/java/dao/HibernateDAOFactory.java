package dao;

import hibernate.HibernateUtil;

public class HibernateDAOFactory extends DAOFactory {

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
      throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
    }
  }

}
