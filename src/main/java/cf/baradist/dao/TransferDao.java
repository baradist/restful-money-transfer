package cf.baradist.dao;

import cf.baradist.model.Transfer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TransferDao extends Dao {
    List<Transfer> getAll() throws SQLException;

    List<Transfer> getTransferByFromAccountId(Long fromAccountId) throws SQLException;

    List<Transfer> getTransferByToAccountId(Long toAccountId) throws SQLException;

    List<Transfer> getTransferByFromAccountIdAndToAccountId(Long fromAccountId, Long toAccountId) throws SQLException;

    Optional<Transfer> getById(Long id) throws SQLException;

    Long commit(Transfer transfer) throws SQLException;
}
