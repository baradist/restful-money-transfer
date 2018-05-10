package cf.baradist.dao;

import cf.baradist.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers();

    Optional<User> getUserById(long userId);

    User getUserByName(String userName);

    long insert(User user);

    void updateUser(Long userId, User user);

    void deleteUser(long userId);
}
