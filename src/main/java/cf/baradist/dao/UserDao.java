package cf.baradist.dao;

import cf.baradist.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao {
    List<User> getAll();

    Optional<User> getById(Long id);

    Long insert(User user);

    void update(Long id, User user);

    void delete(Long id);
}
