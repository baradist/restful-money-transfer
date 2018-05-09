package cf.baradist.dao;

import cf.baradist.model.User;

import java.sql.*;
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
        try (Connection conn = H2DaoFactory.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO user (name, email) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("addUser(): failed to insert user " + user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("addUser(): failed to insert user " + user, e);
        }
    }

    @Override
    public long updateUser(Long userId, User user) {
        try (Connection conn = H2DaoFactory.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE user SET name = ?, email = ? WHERE id = ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setLong(3, userId);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("updateUser(): failed to update user " + userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("updateUser(): failed to update user " + userId, e);
        }
    }

    @Override
    public long deleteUser(long userId) {
        try (Connection conn = H2DaoFactory.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE user WHERE id = ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, userId);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("updateUser(): failed to update user " + userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("updateUser(): failed to update user " + userId, e);
        }
    }
}
