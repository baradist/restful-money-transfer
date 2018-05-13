package cf.baradist.dao.h2;

import cf.baradist.dao.UserDao;
import cf.baradist.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface H2UserDaoImpl extends UserDao {

    String ID = "id";
    String NAME = "name";
    String EMAIL = "email";

    @Override
    default List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection()) {
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
    default Optional<User> getById(Long id) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, name, email FROM user WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return Optional.ofNullable(!rs.next() ? null :
                    new User(id,
                            rs.getString("name"),
                            rs.getString("email")));
        } catch (SQLException e) {
            throw new RuntimeException("Can't read user with id = " + id, e);
        }
    }

    @Override
    default Long insert(User user) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO user (name, email) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("insert(): failed to insert user " + user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("insert(): failed to insert user " + user, e);
        }
    }

    @Override
    default void update(Long id, User user) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE user SET name = ?, email = ? WHERE id = ?");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setLong(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("update(): failed to update user " + id, e);
        }
    }

    @Override
    default void delete(Long id) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE user WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.getGeneratedKeys();
        } catch (SQLException e) {
            throw new RuntimeException("delete(): failed to delete user " + id, e);
        }
    }
}
