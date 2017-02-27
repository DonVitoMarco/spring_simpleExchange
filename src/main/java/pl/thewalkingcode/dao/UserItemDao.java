package pl.thewalkingcode.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.thewalkingcode.model.UserItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository("userItemDao")
public class UserItemDao implements GenericDao<UserItem, String> {

    private JdbcTemplate jdbcTemplate;

    private static final String READ_BY_USERNAME = "SELECT * FROM useritems WHERE username = ?";
    private static final String READ = "SELECT * FROM useritems WHERE id_useritem = ?";
    private static final String CREATE = "INSERT INTO useritems (id_useritem, code, unit, price, username, amount) values (?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM useritems WHERE id_useritem = ?";

    @Autowired
    public UserItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(UserItem userItem) {
        this.jdbcTemplate.update(CREATE,
                userItem.getUuid(),
                userItem.getCode(),
                userItem.getUnit(),
                userItem.getPrice(),
                userItem.getUsername(),
                userItem.getAmount());
    }

    @Override
    public void update(UserItem userItem) {

    }

    @Override
    public UserItem read(String pk) {
        UserItem userItem = this.jdbcTemplate.queryForObject(READ, new Object[]{pk},
                new RowMapper<UserItem>() {
                    @Override
                    public UserItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                        UserItem userItem = new UserItem();
                        userItem.setUuid(rs.getString("id_useritem"));
                        userItem.setCode(rs.getString("code"));
                        userItem.setPrice(rs.getString("price"));
                        userItem.setUnit(rs.getLong("unit"));
                        userItem.setAmount(rs.getString("amount"));
                        userItem.setUsername(rs.getString("username"));
                        return userItem;
                    }
                });
        return userItem;
    }

    public void delete(String pk) {
        jdbcTemplate.update(DELETE, pk);
    }

    public List<UserItem> readAllItemsByUsername(String username) {
        List<UserItem> userItems = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(READ_BY_USERNAME, username);
        for (Map row : rows) {
            UserItem userItem = new UserItem();
            userItem.setUuid((String) row.get("id_useritem"));
            userItem.setCode((String) row.get("code"));
            userItem.setPrice((String) row.get("price"));
            userItem.setUnit((Long) row.get("unit"));
            userItem.setAmount((String) row.get("amount"));
            userItem.setUsername((String) row.get("username"));
            userItems.add(userItem);
        }
        if (userItems.size() == 0) {
            return null;
        }
        return userItems;
    }

}
