package hf.app.bookview.dao;

import java.util.List;

public interface IDao<T> {
    /**
     * CRUD
     */

    List<T> getAll();
    T find(int id);

    boolean add(T item);
    boolean update(T item);

    boolean delete(T item);
    boolean delete(int id);

}
