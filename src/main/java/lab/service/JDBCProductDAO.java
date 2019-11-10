
package lab.service;

import lab.dao.ProductDAO;
import lab.entity.Product;
import lab.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JDBCProductDAO implements ProductDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCProductDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JDBCProductDAO() {

    }

    public Product getById(Long id) {
        String SQL_FIND_PERSON = "select * from PRODUCTS where id = ?";
        Product product = jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[]{id}, new ProductMapper());
        return product;
    }

    public List<Product> getAll() {
        String SQL_GET_ALL = "select * from PRODUCTS";
        List<Product> products = jdbcTemplate.query(SQL_GET_ALL, new ProductMapper());
        return products;
    }

    public void delete(Product product) {
        String SQL_DELETE_PERSON = "delete from PRODUCTS where id = ?";
        jdbcTemplate.update(SQL_DELETE_PERSON, product.getId());
    }

    public void update(Product product) {
        String SQL_UPDATE_PERSON = "update PRODUCTS set cost= ?, name  = ? where id = ?";
        jdbcTemplate.update(SQL_UPDATE_PERSON, product.getCost(), product.getName(), product.getId());
    }

    public void create(Product product) {
        String SQL_INSERT_PERSON = "insert into PRODUCTS (id, cost, name) values(?,?,?)";
        jdbcTemplate.update(SQL_INSERT_PERSON, product.getId(), product.getCost(), product.getName());
    }
}
