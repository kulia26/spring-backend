package lab.dao;

import java.util.List;

import lab.entity.User;

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