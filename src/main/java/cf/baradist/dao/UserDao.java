package cf.baradist.dao;

import cf.baradist.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao {
    List<User> getAll() throws SQLException;

    Optional<User> getById(Long id) throws SQLException;

    Long insert(User user) throws SQLException;

    void update(Long id, User user) throws SQLException;

    void delete(Long id) throws SQLException;
}
