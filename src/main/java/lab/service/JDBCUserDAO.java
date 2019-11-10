
package lab.service;

import java.util.List;

import javax.sql.DataSource;
import lab.entity.User;

import lab.dao.UserDAO;
import lab.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
        User user = jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[]{id}, new UserMapper());
        return user;
    }

    public List<User> getAll() {
        String SQL_GET_ALL = "select * from USERS";
        List<User> users = jdbcTemplate.query(SQL_GET_ALL, new UserMapper());
        return users;
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
