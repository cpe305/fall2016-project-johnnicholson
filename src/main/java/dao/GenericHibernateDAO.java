package dao;

import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericHibernateDAO<T> implements GenericDAO<T> {

  private Class<T> persistentClass;
  private Session session;

  @SuppressWarnings("unchecked")
  public GenericHibernateDAO() {
    this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
  }

  public void setSession(Session s) {
    this.session = s;
  }

  protected Session getSession() {
    if (session == null) {
      throw new IllegalStateException("Session has not been set on DAO before usage");
    }
    return session;
  }

  public Class<T> getPersistentClass() {
    return persistentClass;
  }

  public T findById(Integer id) {
    return (T) getSession().get(getPersistentClass(), id);
  }

  @SuppressWarnings("unchecked")
  public List<T> findAll() {
    return getSession().createCriteria(getPersistentClass()).list();
  }

  public T makePersistent(T entity) {
    getSession().saveOrUpdate(entity);
    return entity;
  }

  public void makeTransient(T entity) {
    getSession().delete(entity);
  }

  public void flush() {
    getSession().flush();
  }

  public void clear() {
    getSession().clear();
  }

}
