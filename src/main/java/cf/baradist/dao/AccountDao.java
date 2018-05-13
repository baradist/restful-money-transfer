package cf.baradist.dao;

import cf.baradist.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDao extends Dao {
    List<Account> getAll();

    List<Account> getAllByUserId(Long userId);

    Optional<Account> getById(Long id);

    Long insert(Account account);

    void update(Long id, Account account);

    void delete(Long id);
}
