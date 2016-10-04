package dao;

import java.util.List;

public interface GenericDAO<T> {

  T findById(Integer id);

  List<T> findAll();

  T makePersistent(T entity);

  void makeTransient(T entity);
}
