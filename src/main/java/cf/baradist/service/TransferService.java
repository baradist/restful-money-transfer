package cf.baradist.service;

import cf.baradist.dao.AccountDao;
import cf.baradist.dao.TransferDao;
import cf.baradist.exception.ApiException;
import cf.baradist.exception.BadRequestException;
import cf.baradist.exception.NotFoundException;
import cf.baradist.model.Account;
import cf.baradist.model.Transfer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TransferService {
    private static final String NOT_FOUND_A_TRANSFER_WITH_ID = "Not found a transfer with ID=";
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

    public Optional<Transfer> getById(Long id) throws SQLException, NotFoundException {
        Optional<Transfer> account = transferDao.getById(id);
        if (!account.isPresent()) {
            throw new NotFoundException(404, NOT_FOUND_A_TRANSFER_WITH_ID + id);
        }
        return account;
    }

    public List<Transfer> getAllTransfers() throws SQLException {
        return transferDao.getAll();
    }

    public List<Transfer> getTransfersByFromAccountId(Long fromAccountId) throws SQLException {
        return transferDao.getTransfersByFromAccountId(fromAccountId);
    }

    public List<Transfer> getTransfersByToAccountId(Long toAccountId) throws SQLException {
        return transferDao.getTransfersByToAccountId(toAccountId);
    }

    public List<Transfer> getTransfersByFromAccountIdAndToAccountId(Long fromAccountId, Long toAccountId) throws SQLException {
        return transferDao.getTransfersByFromAccountIdAndToAccountId(fromAccountId, toAccountId);
    }

    public Optional<Transfer> addTransfer(Transfer transfer) throws ApiException, SQLException {
        Optional<Account> fromAccount = accountDao.getById(transfer.getFromAccountId());
        Optional<Account> toAccount = accountDao.getById(transfer.getToAccountId());
        if (!fromAccount.isPresent() || !toAccount.isPresent()) {
            throw new NotFoundException(
                    "Wrong accountIds: (" + transfer.getFromAccountId() + ", " + transfer.getToAccountId() + ")");
        }
        if (transfer.getCurrencyCode() != fromAccount.get().getCurrency().getIso4217_code()) {
            throw new NotFoundException(406,
                    "Transfer's and FROM account's carrencies aren't the same: ("
                            + transfer.getCurrencyCode() + " "
                            + fromAccount.get().getCurrency().getIso4217_code() + ")");
        }
        if (transfer.getCurrencyCode() != toAccount.get().getCurrency().getIso4217_code()) {
            throw new NotFoundException(406,
                    "Transfer's and TO account's carrencies aren't the same: ("
                            + transfer.getCurrencyCode() + " "
                            + toAccount.get().getCurrency().getIso4217_code() + ")");
        }
        if (fromAccount.get().getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new NotFoundException(406,
                    "Not enough money - expected at least " + transfer.getAmount() +
                    ", but exists only " + fromAccount.get().getBalance());
        }

        Long transferId = transferDao.commit(transfer);
        return transferDao.getById(transferId);
    }

    public void rollbackTransfer(Long transferId) throws ApiException {
        throw new BadRequestException(501, "hasn't been implemented yet"); // TODO
    }
}
