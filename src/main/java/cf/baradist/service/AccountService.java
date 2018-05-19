package cf.baradist.service;

import cf.baradist.dao.AccountDao;
import cf.baradist.exception.NotFoundException;
import cf.baradist.model.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountService {
    private static final String NOT_FOUND_AN_ACCOUNT_WITH_ID = "Not found an account with ID=";
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

    public Optional<Account> getAccountById(Long id) throws SQLException, NotFoundException {
        Optional<Account> account = accountDao.getById(id);
        if (!account.isPresent()) {
            throw new NotFoundException(404, NOT_FOUND_AN_ACCOUNT_WITH_ID + id);
        }
        return account;
    }

    public Optional<Account> addAccount(Account account) throws SQLException {
        Long accountId = accountDao.insert(account);
        account.setId(accountId);
        return Optional.of(account);
    }

    public void updateAccount(Long id, Account account) throws SQLException, NotFoundException {
        if (accountDao.update(id, account) == 0) {
            throw new NotFoundException(404, NOT_FOUND_AN_ACCOUNT_WITH_ID + id);
        }
    }

    public void deleteAccount(Long id) throws SQLException, NotFoundException {
        if (accountDao.delete(id) == 0) {
            throw new NotFoundException(404, NOT_FOUND_AN_ACCOUNT_WITH_ID + id);
        }
    }
}
