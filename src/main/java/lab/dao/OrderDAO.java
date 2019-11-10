package lab.dao;

import lab.entity.Order;

import java.util.List;

public interface OrderDAO extends AbstractDAO<Order> {

    /* create */
    void create(Order order);

    /* read */
    List<Order> getAll();

    Order getById(Long id);

    /* update */
    void update(Order order);

    /* delete */
    void delete(Order order);
}
