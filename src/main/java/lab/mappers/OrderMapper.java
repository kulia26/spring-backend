
package lab.mappers;

import lab.entity.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {

    public Order mapRow(ResultSet resultSet, int i) throws SQLException {

        Order product = new Order();
        product.setId(resultSet.getLong("id"));
        product.setProductId(resultSet.getLong("PRODUCT_ID"));
        product.setUserId(resultSet.getLong("USER_ID"));
        return product;
    }
}
