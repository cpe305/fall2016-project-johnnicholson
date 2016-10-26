package dao;

import hibernate.HibernateUtil;
import model.PrintLocation;

public class PrintLocationDAO extends GenericHibernateDAO<PrintLocation> {

  public PrintLocation findByName(String name) {
    return (PrintLocation) HibernateUtil.getFactory().getCurrentSession()
        .createQuery("from PrintLocation where name = :name").setString("name", name.toLowerCase())
        .uniqueResult();
  }

}
