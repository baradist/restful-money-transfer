package cf.baradist.controller;

import cf.baradist.dao.h2.UserDaoImpl;
import cf.baradist.model.User;
import cf.baradist.service.UserService;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static cf.baradist.JettyServer.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class UserControllerTest {
    private UserController controller;

    @Before
    public void setUp() throws Exception {
        controller = new UserController();
        configureDb();
    }

    @Test
    public void getAllUsers() {
        List<User> users = new ArrayList<User>() {
            {
                add(new User(1L, "John", "john_doe@gmail.com"));
                add(new User(2L, "Jack", "jack_smith@gmail.com"));
                add(new User(3L, "Jane", "jane@gmail.com"));
            }
        };
        assertThat(controller.getAllUsers().getEntity(), is(users));
    }

    @Test
    public void get() {
        Response response = controller.get(1L);
        assertThat(response.getEntity(), is(new User(1L, "John", "john_doe@gmail.com")));
    }

    @Test
    public void add() {
        Response response = controller.add(new User(0L, "NewUser", "AnEmailOfANewUser@mail.com"));
        assertThat(response.getEntity(), is(new User(4L, "NewUser", "AnEmailOfANewUser@mail.com")));
    }

    @Test
    public void update() {
        controller.update(1L, new User(0L, "NewNameOfAnOldUser", "email@mail.com"));
        Response response = controller.get(1L);
        assertThat(response.getEntity(), is(new User(1L, "NewNameOfAnOldUser", "email@mail.com")));
    }

    @Test
    public void delete() {
        controller.delete(1L);
        Response response = controller.get(1L);
        assertNull(response.getEntity());
    }

    private static void configureDb() {
        Properties properties = readProperties(INIT_SQL);
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(properties.getProperty(DS_URL, "jdbc:h2:mem:moneytransfer;DB_CLOSE_DELAY=-1"));
        ds.setUser(properties.getProperty(DS_USER, "sa"));
        ds.setPassword(properties.getProperty(DS_PASSWORD, "sa"));
        UserService.getInstance().setUserDao((UserDaoImpl) ds::getConnection);

        fillTestData(ds);
    }
}
