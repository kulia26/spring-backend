
package lab.service;

import lab.dao.UserDAO;
import lab.entity.User;
import lab.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JDBCUserDAO implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCUserDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JDBCUserDAO() {

    }

    public User getById(Long id) {
        String SQL_FIND_PERSON = "select * from USERS where id = ?";
        return jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[]{id}, new UserMapper());
    }

    public List<User> getAll() {
        String SQL_GET_ALL = "select * from USERS";
        return jdbcTemplate.query(SQL_GET_ALL, new UserMapper());
    }

    public void delete(User user) {
        String SQL_DELETE_PERSON = "delete from USERS where id = ?";
        jdbcTemplate.update(SQL_DELETE_PERSON, user.getId());
    }

    public void update(User user) {
        String SQL_UPDATE_PERSON = "update USERS set phone = ?, address = ?, name  = ? where id = ?";
        jdbcTemplate.update(SQL_UPDATE_PERSON, user.getPhone(), user.getAddress(), user.getName(), user.getId());
    }

    public void create(User user) {
        String SQL_INSERT_PERSON = "insert into USERS (id, phone, address, name) values(?,?,?,?)";
        jdbcTemplate.update(SQL_INSERT_PERSON, user.getId(), user.getPhone(), user.getAddress(), user.getName());
    }
}
