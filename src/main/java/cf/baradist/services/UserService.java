package cf.baradist.services;

import cf.baradist.dao.DaoFactory;
import cf.baradist.dao.UserDao;
import cf.baradist.model.User;

import javax.annotation.Resource;
import java.util.List;

@Resource
public class UserService {
    private static UserService instance = new UserService();
    UserDao userDao = DaoFactory.getDaoFactory().getUserDao();

    public static UserService getInstance() {
        return instance;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
