package cf.baradist.service;

import cf.baradist.dao.AccountDao;
import cf.baradist.model.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountService {
    private static AccountService instance = new AccountService();
    private AccountDao accountDao;

    public static AccountService getInstance() {
        return instance;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> getAllAccounts() throws SQLException {
        return accountDao.getAll();
    }

    public List<Account> getAllAccountsByUserId(Long userId) throws SQLException {
        return accountDao.getAllByUserId(userId);
    }

    public Optional<Account> getAccountById(Long accountId) throws SQLException {
        return accountDao.getById(accountId);
    }

    public Optional<Account> addAccount(Account account) throws SQLException {
        Long accountId = accountDao.insert(account);
        account.setId(accountId);
        return Optional.of(account);
    }

    public void updateAccount(Long accountId, Account account) throws SQLException {
        accountDao.update(accountId, account);
    }

    public void deleteAccount(Long accountId) throws SQLException {
        accountDao.delete(accountId);
    }
}
