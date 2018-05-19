package cf.baradist.service;

import cf.baradist.dao.UserDao;
import cf.baradist.exception.NotFoundException;
import cf.baradist.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static final String NOT_FOUND_A_USER_WITH_ID = "Not found a user with ID=";
    private static UserService instance = new UserService();

    private UserDao userDao;

    public static UserService getInstance() {
        return instance;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() throws SQLException {
        return userDao.getAll();
    }

    public Optional<User> getUserById(Long id) throws SQLException, NotFoundException {
        Optional<User> user = userDao.getById(id);
        if (!user.isPresent()) {
            throw new NotFoundException(404, NOT_FOUND_A_USER_WITH_ID + id);
        }
        return user;
    }

    public Optional<User> addUser(User user) throws SQLException {
        final long userId = userDao.insert(user);
        user.setId(userId);
        return Optional.of(user);
    }

    public void updateUser(Long id, User user) throws SQLException, NotFoundException {
        if (userDao.update(id, user) == 0) {
            throw new NotFoundException(404, NOT_FOUND_A_USER_WITH_ID + id);
        }
    }

    public void deleteUser(Long id) throws SQLException, NotFoundException {
        if (userDao.delete(id) == 0) {
            throw new NotFoundException(404, NOT_FOUND_A_USER_WITH_ID + id);
        }
    }
}
