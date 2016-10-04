package dao;

public abstract class DAOFactory {

  public static DAOFactory instance(Class factory) {
    try {
      return (DAOFactory) factory.newInstance();
    } catch (Exception ex) {
      throw new RuntimeException("Couldn't create DAOFactory: " + factory);
    }
  }

  public abstract PersonDAO getPersonDAO();

}
