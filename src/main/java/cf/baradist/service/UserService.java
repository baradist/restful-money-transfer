package cf.baradist.service;

import cf.baradist.dao.UserDao;
import cf.baradist.model.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static UserService instance = new UserService();
    private UserDao userDao;

    public static UserService getInstance() {
        return instance;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public Optional<User> getUserById(long userId) {
        return userDao.getUserById(userId);
    }

    public Optional<User> addUser(User user) {
        final long userId = userDao.insert(user);
        user.setId(userId);
        return Optional.of(user);
    }

    public void updateUser(long userId, User user) {
        userDao.updateUser(userId, user);
    }

    public void deleteUser(long userId) {
        userDao.deleteUser(userId);
    }
}
