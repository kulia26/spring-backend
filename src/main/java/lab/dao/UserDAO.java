package lab.dao;

import lab.entity.User;

import java.util.List;

public interface UserDAO extends AbstractDAO<User> {

    /* create */
    void create(User user);

    /* read */
    List<User> getAll();

    User getById(Long id);

    /* update */
    void update(User user);

    /* delete */
    void delete(User user);
}