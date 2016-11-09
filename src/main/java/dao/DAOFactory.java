package dao;

import org.apache.log4j.Logger;

public abstract class DAOFactory {
  static Logger lgr = Logger.getLogger(DAOFactory.class);

  public static DAOFactory instance(Class factory) {
    try {
      return (DAOFactory) factory.newInstance();
    } catch (Exception ex) {
      lgr.error(ex);
      throw new IllegalArgumentException();
    }
  }

  public abstract PersonDAO getPersonDAO();

  public abstract PrintLocationDAO getPrintLocationDAO();

  public abstract PrintRequestDAO getPrintRequestDAO();

}
