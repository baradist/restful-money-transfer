package cf.baradist.dao;

import cf.baradist.model.Transfer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TransferDao extends Dao {
    List<Transfer> getAll() throws SQLException;

    List<Transfer> getTransfersByFromAccountId(Long fromAccountId) throws SQLException;

    List<Transfer> getTransfersByToAccountId(Long toAccountId) throws SQLException;

    List<Transfer> getTransfersByFromAccountIdAndToAccountId(Long fromAccountId, Long toAccountId) throws SQLException;

    Optional<Transfer> getById(Long id) throws SQLException;

    Long commit(Transfer transfer) throws SQLException;
}
