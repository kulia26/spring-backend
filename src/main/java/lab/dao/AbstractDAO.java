package lab.dao;

import java.sql.SQLException;
import java.util.List;

public interface AbstractDAO<T> {

    //create
    void create(T t) throws SQLException;

    //read
    List<T> getAll() throws SQLException;
    T getById(Long id) throws SQLException;

    //update
    void update(T order) throws SQLException;

    //delete
    void delete(T order) throws SQLException;

}
