package cf.baradist.service;

import cf.baradist.dao.TransferDao;
import cf.baradist.model.Account;
import cf.baradist.model.Transfer;

import java.util.List;
import java.util.Optional;

public class TransferService {
    private static TransferService instance = new TransferService();
    private TransferDao transferDao;

    public static TransferService getInstance() {
        return instance;
    }

    public void setTransferDao(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    public List<Transfer> getAllTransfers() {
        return null;
    }

    public List<Transfer> getTransferByFromAccountId(Long fromAccountId) {
        return null;
    }

    public List<Transfer> getTransferByToAccountId(Long toAccountId) {
        return null;
    }

    public List<Transfer> getTransferByFromAccountIdAndToAccountId(Long fromAccountId, Long toAccountId) {
        return null;
    }

    public Optional<Account> addTransfer(Transfer transfer) {
        return null;
    }

    public void updateTransfer(Long transferId, Transfer transfer) {

    }

    public void deleteTransfer(Long transferId) {

    }
}
