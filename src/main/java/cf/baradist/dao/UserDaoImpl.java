package cf.baradist.dao;

import cf.baradist.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String SELECT_FROM_USER = "SELECT * FROM user";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = H2DaoFactory.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(SELECT_FROM_USER);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt(ID),
                        rs.getString(NAME),
                        rs.getString(EMAIL)));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Can't read data", e);
        }
    }

    @Override
    public User getUserById(long userId) {
        return null;
    }

    @Override
    public User getUserByName(String userName) {
        return null;
    }

    @Override
    public long addUser(User user) {
        return 0;
    }

    @Override
    public int updateUser(Long userId, User user) {
        return 0;
    }

    @Override
    public int deleteUser(long userId) {
        return 0;
    }
}
