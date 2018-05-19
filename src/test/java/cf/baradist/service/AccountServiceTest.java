package cf.baradist.service;

import cf.baradist.dao.AccountDao;
import cf.baradist.model.Account;
import cf.baradist.model.Currency;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    private static final Currency USD = new Currency(840, "USD", "United States dollar");
    private static final Currency EUR = new Currency(978, "EUR", "Euro");

    @Mock
    private AccountDao accountDao;

    private AccountService accountService;
    private Account account;
    private List<Account> accounts;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        accountService = new AccountService();
        accountService.setAccountDao(accountDao);

        account = new Account(1L, 1L, new BigDecimal("77.70"), USD);

        accounts = new ArrayList<Account>() {
            {
                add(account);
                add(new Account(2L, 1L, new BigDecimal("0.00"), USD));
                add(new Account(3L, 2L, new BigDecimal("77.70"), USD));
                add(new Account(4L, 2L, new BigDecimal("42.30"), EUR));
                add(new Account(5L, 3L, new BigDecimal("77.70"), EUR));
            }
        };
    }

    @Test
    public void getAllAccountsTest() throws Exception {
        when(accountDao.getAll()).thenReturn(accounts);
        assertThat(accountService.getAllAccounts(), is(accounts));
    }

    @Test
    public void getAllAccountsByUserIdTest() throws Exception {
        List<Account> filteredAccounts = accounts.stream().filter(a -> a.getUserId() == 2L).collect(Collectors.toList());
        when(accountDao.getAllByUserId(2L)).thenReturn(filteredAccounts);
        assertThat(accountService.getAllAccountsByUserId(2L), is(filteredAccounts));
    }

    @Test
    public void getAccountByIdTest() throws Exception {
        when(accountDao.getById(1L)).thenReturn(Optional.of(account));
        assertThat(accountService.getAccountById(1L).get(), is(account));
    }

    @Test
    public void addAccountTest() throws Exception {
        when(accountDao.insert(account)).thenReturn(6L);
        Account account1 = new Account(6L, 1L, new BigDecimal("77.70"), USD);
        assertThat(accountService.addAccount(account).get(), is(account1));
    }

    @Test
    public void updateAccountTest() throws Exception {
        accountService.updateAccount(0L, account);
        verify(accountDao).update(0L, account);
    }

    @Test
    public void deleteAccountTest() throws Exception {
        accountService.deleteAccount(0L);
        verify(accountDao).delete(0L);
    }
}
