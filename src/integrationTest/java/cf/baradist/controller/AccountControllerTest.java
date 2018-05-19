package cf.baradist.controller;

import cf.baradist.AbstractTest;
import cf.baradist.exception.NotFoundException;
import cf.baradist.model.Account;
import cf.baradist.model.Currency;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AccountControllerTest extends AbstractTest {
    private static final Currency USD = new Currency(840, "USD", "United States dollar");
    private static final Currency EUR = new Currency(978, "EUR", "Euro");
    private AccountController controller;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        controller = new AccountController();
    }

    @Test
    public void getAllAccountsByUserId() throws Exception {
        List<Account> accounts = new ArrayList<Account>() {
            {
                add(new Account(1L, 1L, new BigDecimal("77.70"), USD));
                add(new Account(2L, 1L, new BigDecimal("0.00"), USD));
                add(new Account(3L, 2L, new BigDecimal("77.70"), USD));
                add(new Account(4L, 2L, new BigDecimal("42.30"), EUR));
                add(new Account(5L, 3L, new BigDecimal("77.70"), EUR));
            }
        };

        assertThat(controller.getAllAccountsByUserId(1L).getEntity(),
                is(accounts.stream().filter(a -> a.getUserId() == 1L).collect(Collectors.toList())));
        assertThat(controller.getAllAccountsByUserId(2L).getEntity(),
                is(accounts.stream().filter(a -> a.getUserId() == 2L).collect(Collectors.toList())));
        Response response = controller.getAllAccountsByUserId(3L);
        assertThat(response.getEntity(),
                is(accounts.stream().filter(a -> a.getUserId() == 3L).collect(Collectors.toList())));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test
    public void get() throws Exception {
        Response response = controller.get(1L);
        assertThat(response.getEntity(),
                is(new Account(1L, 1L, new BigDecimal("77.70"), USD)));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));

        response = controller.get(2L);
        assertThat(response.getEntity(),
                is(new Account(2L, 1L, new BigDecimal("0.00"), USD)));
    }

    @Test(expected = NotFoundException.class)
    public void getNotExisting() throws Exception {
        controller.get(77L);
    }

    @Test
    public void add() throws Exception {
        Response response = controller.add(new Account(0L, 2L, new BigDecimal("42.30"), EUR));
        assertThat(response.getEntity(), is(new Account(6L, 2L, new BigDecimal("42.30"), EUR)));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test
    public void update() throws Exception {
        controller.update(1L, new Account(0L, 1L, new BigDecimal("88.88"), EUR));
        Response response = controller.get(1L);
        assertThat(response.getEntity(), is(new Account(1L, 1L, new BigDecimal("88.88"), EUR)));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test
    public void updateUserIdDoesntChange() throws Exception {
        controller.update(1L, new Account(0L, 2L, new BigDecimal("88.88"), EUR));
        Response response = controller.get(1L);
        assertThat(response.getEntity(), is(new Account(1L, 1L, new BigDecimal("88.88"), EUR)));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotExisting() throws Exception {
        controller.update(77L, new Account(77L, 1L, new BigDecimal("88.88"), EUR));
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        controller.delete(1L);
        controller.get(1L); // shouldn't be found
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotExisting() throws Exception {
        controller.delete(77L);
    }
}
