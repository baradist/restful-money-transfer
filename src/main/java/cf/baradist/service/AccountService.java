package cf.baradist.service;

import cf.baradist.dao.AccountDao;
import cf.baradist.model.Account;

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

    public List<Account> getAllAccounts() {
        return accountDao.getAll();
    }

    public List<Account> getAllAccountsByUserId(Long userId) {
        return accountDao.getAllByUserId(userId);
    }

    public Optional<Account> getAccountById(Long accountId) {
        return accountDao.getById(accountId);
    }

    public Optional<Account> addAccount(Account account) {
        final Long accountId = accountDao.insert(account);
        account.setId(accountId);
        return Optional.of(account);
    }

    public void updateAccount(Long accountId, Account account) {
        accountDao.update(accountId, account);
    }

    public void deleteAccount(Long accountId) {
        accountDao.delete(accountId);
    }
}
