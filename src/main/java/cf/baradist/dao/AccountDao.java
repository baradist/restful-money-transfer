package cf.baradist.dao;

import cf.baradist.model.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountDao extends Dao {
    List<Account> getAll() throws SQLException;

    List<Account> getAllByUserId(Long userId) throws SQLException;

    Optional<Account> getById(Long id) throws SQLException;

    Long insert(Account account) throws SQLException;

    void update(Long id, Account account) throws SQLException;

    void delete(Long id) throws SQLException;
}
