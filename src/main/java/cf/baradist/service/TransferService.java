package cf.baradist.service;

import cf.baradist.dao.AccountDao;
import cf.baradist.dao.TransferDao;
import cf.baradist.model.Account;
import cf.baradist.model.Transfer;

import java.util.List;
import java.util.Optional;

public class TransferService {
    private static TransferService instance = new TransferService();
    private TransferDao transferDao;
    private AccountDao accountDao;

    public static TransferService getInstance() {
        return instance;
    }

    public void setTransferDao(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Optional<Transfer> getById(Long id) {
        return transferDao.getById(id);
    }

    public List<Transfer> getAllTransfers() {
        return transferDao.getAll();
    }

    public List<Transfer> getTransfersByFromAccountId(Long fromAccountId) {
        return transferDao.getTransferByFromAccountId(fromAccountId);
    }

    public List<Transfer> getTransfersByToAccountId(Long toAccountId) {
        return transferDao.getTransferByToAccountId(toAccountId);
    }

    public List<Transfer> getTransfersByFromAccountIdAndToAccountId(Long fromAccountId, Long toAccountId) {
        return transferDao.getTransferByFromAccountIdAndToAccountId(fromAccountId, toAccountId);
    }

    public Optional<Transfer> addTransfer(Transfer transfer) {
        Optional<Account> fromAccount = accountDao.getById(transfer.getFromAccountId());
        Optional<Account> toAccount = accountDao.getById(transfer.getToAccountId());
        if (!fromAccount.isPresent() || !toAccount.isPresent()) {
            throw new RuntimeException(
                    "Wrong accountIds: (" + transfer.getFromAccountId() + ", " + transfer.getToAccountId() + ")");
        }
        if (transfer.getCurrencyCode() != fromAccount.get().getCurrency().getIso4217_code()) {
            throw new RuntimeException("Currency of the transfer and of a From account aren't the same: ("
                    + transfer.getCurrencyCode() + " "
                    + fromAccount.get().getCurrency().getIso4217_code() + ")");
        }
        if (transfer.getCurrencyCode() != toAccount.get().getCurrency().getIso4217_code()) {
            throw new RuntimeException("Currency of the transfer and of a To account aren't the same: ("
                    + transfer.getCurrencyCode() + " "
                    + toAccount.get().getCurrency().getIso4217_code() + ")");
        }
        if (fromAccount.get().getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new RuntimeException("Not enough money - expected at least " + transfer.getAmount() +
                    ", but exists only " + fromAccount.get().getBalance());
        }

        Long transferId = transferDao.commit(transfer);
        return transferDao.getById(transferId);
    }

    public void rollbackTransfer(Long transferId) {
        throw new RuntimeException("hasn't been implemented yet");// TODO
    }
}
