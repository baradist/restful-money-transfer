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
        return userDao.getAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userDao.getById(userId);
    }

    public Optional<User> addUser(User user) {
        final long userId = userDao.insert(user);
        user.setId(userId);
        return Optional.of(user);
    }

    public void updateUser(Long userId, User user) {
        userDao.update(userId, user);
    }

    public void deleteUser(Long userId) {
        userDao.delete(userId);
    }
}
