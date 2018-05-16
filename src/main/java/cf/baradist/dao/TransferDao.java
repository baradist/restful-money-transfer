package cf.baradist.dao;

import cf.baradist.model.Transfer;

import java.util.List;
import java.util.Optional;

public interface TransferDao extends Dao {
    List<Transfer> getAll();

    List<Transfer> getTransferByFromAccountId(Long fromAccountId);

    List<Transfer> getTransferByToAccountId(Long toAccountId);

    List<Transfer> getTransferByFromAccountIdAndToAccountId(Long fromAccountId, Long toAccountId);

    Optional<Transfer> getById(Long id);

    Long commit(Transfer transfer);
}
