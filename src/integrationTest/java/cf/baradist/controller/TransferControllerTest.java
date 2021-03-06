package cf.baradist.controller;

import cf.baradist.AbstractTest;
import cf.baradist.exception.NotFoundException;
import cf.baradist.model.Account;
import cf.baradist.model.Transfer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TransferControllerTest extends AbstractTest {
    private TransferController controller;
    private AccountController accountController;
    private Transfer transfer;
    private List<Transfer> transfers;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        controller = new TransferController();
        accountController = new AccountController();
        transfer = new Transfer(1L, 840, new BigDecimal("77.70"), 3L, 1L);
        transfers = new ArrayList<Transfer>() {
            {
                add(transfer);
                add(new Transfer(2L, 978, new BigDecimal("5.50"), 4L, 5L));
            }
        };

    }

    @Test
    public void get() throws Exception {
        Response response = controller.getTransferById(1L);
        assertThat(response.getEntity(), is(transfer));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test
    public void getAllUsers() throws Exception {
        final Response response = controller.getAllTransfers();
        assertThat(response.getEntity(), is(transfers));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test
    public void getTransfersByFromAccountId() throws Exception {
        Response response = controller.getTransfersByFromAccountId(3L);
        assertThat(response.getEntity(),
                is(transfers.stream().filter(t -> t.getFromAccountId() == 3L).collect(Collectors.toList())));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test
    public void getTransfersByToAccountId() throws Exception {
        Response response = controller.getTransfersByToAccountId(1L);
        assertThat(response.getEntity(),
                is(transfers.stream().filter(t -> t.getToAccountId() == 1L).collect(Collectors.toList())));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test
    public void getTransfersByFromAccountIdAndToAccountId() throws Exception {
        Response response = controller.getTransfersByFromAccountIdAndToAccountId(3L, 1L);
        assertThat(response.getEntity(),
                is(transfers.stream().filter(t ->
                        t.getFromAccountId() == 3L && t.getToAccountId() == 1L).collect(Collectors.toList())));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Test(expected = NotFoundException.class)
    public void getNotExisting() throws Exception {
        Response response = controller.getTransferById(77L);
    }

    @Test
    public void add() throws Exception {
        Response response = controller.addTransfer(new Transfer(0L, 840, new BigDecimal("10.00"), 3L, 1L));
        Account from = (Account) accountController.getAccountById(3L).getEntity();
        Account to = (Account) accountController.getAccountById(1L).getEntity();
        assertThat(from.getBalance(), is(new BigDecimal("67.70")));
        assertThat(to.getBalance(), is(new BigDecimal("87.70")));

        assertThat(response.getEntity(), is(new Transfer(3L, 840, new BigDecimal("10.00"), 3L, 1L)));
        assertThat(response.getStatusInfo(), is(Response.Status.OK));
    }

    @Ignore
    @Test
    public void delete() throws Exception {
        final Transfer removableTransfer = (Transfer) controller.getTransferById(1L).getEntity();

        Response response = controller.deleteTransfer(removableTransfer.getId());
        assertThat(response.getStatusInfo(), is(Response.Status.OK));

        Account from = (Account) accountController.getAccountById(removableTransfer.getFromAccountId()).getEntity();
        Account to = (Account) accountController.getAccountById(removableTransfer.getToAccountId()).getEntity();

        assertThat(from.getBalance(), is(new BigDecimal("155.40")));
        assertThat(to.getBalance(), is(new BigDecimal("0.00")));

    }
}
