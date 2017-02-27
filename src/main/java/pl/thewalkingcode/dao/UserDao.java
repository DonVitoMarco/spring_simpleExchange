package pl.thewalkingcode.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.thewalkingcode.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository("userDao")
public class UserDao implements GenericDao<User, String> {

    private JdbcTemplate jdbcTemplate;

    private static final String READ = "SELECT * FROM users WHERE username = ?";
    private static final String UPDATE_WALLET = "UPDATE users SET wallet = ? WHERE username = ?";
    private static final String CREATE = "INSERT INTO users (username, password, enabled, user_role, wallet) values (?, ?, ?, ?, ?)";
    private static final String CHECK_USER = "SELECT COUNT(*) FROM users WHERE username = ?";

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {
        this.jdbcTemplate.update(CREATE,
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.getUserRole(),
                user.getWallet());
    }

    @Override
    public void update(User user) {
        User temp = read(user.getUsername());
        if(!temp.getWallet().equals(user.getWallet())) {
            this.jdbcTemplate.update(UPDATE_WALLET, user.getWallet(), user.getUsername());
        }
    }

    @Override
    public User read(String pk) {
        User user = this.jdbcTemplate.queryForObject(READ, new Object[]{pk},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setWallet(rs.getBigDecimal("wallet"));
                        user.setEnabled(rs.getBoolean("enabled"));
                        user.setUserRole(rs.getInt("user_role"));
                        return user;
                    }
                });
        return user;
    }

    public int checkUsername(String username) {
        return this.jdbcTemplate.queryForObject(CHECK_USER, Integer.class, username);
    }

}
