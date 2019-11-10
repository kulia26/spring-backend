
package lab.mappers;

import lab.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet resultSet, int i) throws SQLException {

        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setCost(resultSet.getInt("cost"));
        product.setName(resultSet.getString("name"));
        return product;
    }
}
