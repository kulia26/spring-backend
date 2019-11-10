package lab.dao;


import lab.entity.Product;

import java.util.List;

public interface ProductDAO extends AbstractDAO<Product> {

    /* create */
    void create(Product product);

    /* read */
    List<Product> getAll();

    Product getById(Long id);

    /* update */
    void update(Product product);

    /* delete */
    void delete(Product product);
}
