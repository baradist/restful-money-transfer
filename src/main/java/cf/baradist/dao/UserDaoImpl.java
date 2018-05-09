package cf.baradist.dao;

import cf.baradist.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = H2DaoFactory.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, name, email FROM user");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getLong(ID),
                        rs.getString(NAME),
                        rs.getString(EMAIL)));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Can't read data", e);
        }
    }

    @Override
    public Optional<User> getUserById(long userId) {
        try (Connection conn = H2DaoFactory.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, name, email FROM user WHERE id = ?");
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            return Optional.ofNullable(!rs.next() ? null :
                    new User(userId,
                            rs.getString("name"),
                            rs.getString("email")));
        } catch (SQLException e) {
            throw new RuntimeException("Can't read user with id = " + userId, e);
        }
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
