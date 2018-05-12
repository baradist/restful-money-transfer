package cf.baradist.service;

import cf.baradist.dao.UserDao;
import cf.baradist.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserDao userDao;
    private User user;

    @Before
    public void setUp() throws Exception {
        userService = new UserService();
        userService.setUserDao(userDao = mock(UserDao.class));

        user = new User(1l, "Name1", "Email1");
    }

    @Test
    public void getAllUsersTest() {
        List<User> users = Arrays.asList(new User(0l, "Name", "Email"),
                new User(1l, "Name1", "Email1"),
                new User(2l, "Name2", "Email2"));
        when(userDao.getAllUsers()).thenReturn(users);
        assertEquals(users, userService.getAllUsers());
    }

    @Test
    public void getUserByIdTest() {
        when(userDao.getUserById(1)).thenReturn(Optional.of(user));
        assertEquals(user, userService.getUserById(1).get());
    }

    @Test
    public void addUserTest() {
        when(userDao.insert(user)).thenReturn(0l);
        User user1 = new User(0l, "Name1", "Email1");
        assertEquals(user1, userService.addUser(user).get());
    }

    @Test
    public void updateUserTest() {
        userService.updateUser(0l, user);
        verify(userDao).updateUser(0l, user);
    }

    @Test
    public void deleteUserTest() {
        userService.deleteUser(0l);
        verify(userDao).deleteUser(0l);
    }
}
