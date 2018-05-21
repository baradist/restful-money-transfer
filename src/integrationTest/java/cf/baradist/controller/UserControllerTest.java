package cf.baradist.controller;

import cf.baradist.AbstractTest;
import cf.baradist.exception.NotFoundException;
import cf.baradist.model.User;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserControllerTest extends AbstractTest {
    private UserController controller;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        controller = new UserController();
    }

    @Test
    public void getAllUsers() throws Exception {
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
    public void get() throws Exception {
        Response response = controller.getUserById(1L);
        assertThat(response.getEntity(), is(new User(1L, "John", "john_doe@gmail.com")));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test(expected = NotFoundException.class)
    public void getNotExisting() throws Exception {
        controller.getUserById(77L);
    }

    @Test
    public void add() throws Exception {
        Response response = controller.addUser(new User(0L, "NewUser", "AnEmailOfANewUser@mail.com"));
        assertThat(response.getEntity(), is(new User(4L, "NewUser", "AnEmailOfANewUser@mail.com")));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test
    public void update() throws Exception {
        controller.updateUser(1L, new User(0L, "NewNameOfAnOldUser", "email@mail.com"));
        Response response = controller.getUserById(1L);
        assertThat(response.getEntity(), is(new User(1L, "NewNameOfAnOldUser", "email@mail.com")));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        controller.deleteUser(1L);
        controller.getUserById(1L); // shouldn't be found
    }
}
