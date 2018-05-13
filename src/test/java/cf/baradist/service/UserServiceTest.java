package cf.baradist.service;

import cf.baradist.dao.UserDao;
import cf.baradist.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserDao userDao;

    private UserService userService;
    private User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userService = new UserService();
        userService.setUserDao(userDao);

        user = new User(1L, "Name1", "Email1");
    }

    @Test
    public void getAllUsersTest() {
        List<User> users = new ArrayList<User>() {
            {
                add(user);
                add(new User(2L, "Name2", "Email2"));
                add(new User(3L, "Name3", "Email3"));
            }
        };
        when(userDao.getAll()).thenReturn(users);
        assertThat(userService.getAllUsers(), is(users));
    }

    @Test
    public void getUserByIdTest() {
        when(userDao.getById(1L)).thenReturn(Optional.of(user));
        assertThat(userService.getUserById(1L).get(), is(user));
    }

    @Test
    public void addUserTest() {
        when(userDao.insert(user)).thenReturn(4L);
        User user1 = new User(4L, "Name1", "Email1");
        assertThat(userService.addUser(user).get(), is(user1));
    }

    @Test
    public void updateUserTest() {
        userService.updateUser(0L, user);
        verify(userDao).update(0L, user);
    }

    @Test
    public void deleteUserTest() {
        userService.deleteUser(0L);
        verify(userDao).delete(0L);
    }
}
