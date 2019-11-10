
package lab.service;

import lab.dao.OrderDAO;
import lab.entity.Order;
import lab.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JDBCOrderDAO implements OrderDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCOrderDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JDBCOrderDAO() {

    }

    public Order getById(Long id) {
        String SQL_FIND_PERSON = "select * from ORDERS where id = ?";
        return jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[]{id}, new OrderMapper());
    }

    public List<Order> getAll() {
        String SQL_GET_ALL = "select * from ORDERS";
        return jdbcTemplate.query(SQL_GET_ALL, new OrderMapper());
    }

    public void delete(Order order) {
        String SQL_DELETE_PERSON = "delete from ORDERS where id = ?";
        jdbcTemplate.update(SQL_DELETE_PERSON, order.getId());
    }

    public void update(Order order) {
        String SQL_UPDATE_PERSON = "update ORDERS set PRODUCT_ID = ?, USER_ID = ? where id = ?";
        jdbcTemplate.update(SQL_UPDATE_PERSON, order.getProductId(), order.getUserId(), order.getId());
    }

    public void create(Order order) {
        String SQL_INSERT_PERSON = "insert into ORDERS (id, USER_ID, PRODUCT_ID) values(?,?,?)";
        jdbcTemplate.update(SQL_INSERT_PERSON, order.getId(), order.getUserId(), order.getProductId());
    }
}
