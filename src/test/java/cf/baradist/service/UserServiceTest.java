package cf.baradist.service;

import cf.baradist.dao.UserDao;
import cf.baradist.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserService userService;
    UserDao userDao;

    @Before
    public void setUp() throws Exception {
        userService = new UserService();
        userService.setUserDao(userDao = mock(UserDao.class));
    }

    @Test
    public void getAllUsers() {
        List<User> users = Arrays.asList(new User(0, "Name", "Email"),
                new User(1, "Name1", "Email1"),
                new User(2, "Name2", "Email2"));
        when(userDao.getAllUsers()).thenReturn(users);

        assertEquals(users, userService.getAllUsers());
    }

    @Test
    public void getUserById() {
    }

    @Test
    public void addUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUser() {
    }
}