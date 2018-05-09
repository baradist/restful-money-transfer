package cf.baradist.service;

import cf.baradist.dao.DaoFactory;
import cf.baradist.dao.UserDao;
import cf.baradist.model.User;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Resource
public class UserService {
    private static UserService instance = new UserService();
    private UserDao userDao = DaoFactory.getDaoFactory().getUserDao();

    public static UserService getInstance() {
        return instance;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public Optional<User> getUserById(long userId) {
        return userDao.getUserById(userId);
    }
}
