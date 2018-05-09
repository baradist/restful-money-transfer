package cf.baradist.dao;

import cf.baradist.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers();

    Optional<User> getUserById(long userId);

    User getUserByName(String userName);

    long addUser(User user);

    long updateUser(Long userId, User user);

    long deleteUser(long userId);
}
