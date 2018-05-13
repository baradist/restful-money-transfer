package cf.baradist.dao.h2;

import cf.baradist.dao.AccountDao;
import cf.baradist.dao.DaoHandler;
import cf.baradist.model.Account;
import cf.baradist.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface H2AccountDaoImpl extends AccountDao {

    String ID = "id";
    String USER_ID = "userId";
    String BALANCE = "balance";
    String CURRENCY = "currency";

    @Override
    default List<Account> getAll() {
        return getAllByUserId(null);
    }

    @Override
    default List<Account> getAllByUserId(Long userId) {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement stmt;
            String sql = "SELECT id, userId, balance, currency FROM account ";
            if (userId != null) {
                sql += " WHERE userId = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setLong(1, userId);
            } else {
                stmt = conn.prepareStatement(sql);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                accounts.add(new Account(
                        rs.getLong(ID),
                        rs.getLong(USER_ID),
                        rs.getBigDecimal(BALANCE),
                        new Currency(((H2CurrencyDaoImpl) DaoHandler.getDaoByClass(Currency.class)).getById(rs.getInt(CURRENCY)).get())
                ));
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException("Can't read data", e);
        }
    }

    @Override
    default Optional<Account> getById(Long id) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt;
            stmt = conn.prepareStatement("SELECT id, userId, balance, currency FROM account WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return Optional.ofNullable(!rs.next() ? null :
                    new Account(
                            rs.getLong(ID),
                            rs.getLong(USER_ID),
                            rs.getBigDecimal(BALANCE),
                            new Currency(((H2CurrencyDaoImpl) DaoHandler.getDaoByClass(Currency.class)).getById(rs.getInt(CURRENCY)).get())
                    ));
        } catch (SQLException e) {
            throw new RuntimeException("Can't read user with id = " + id, e);
        }
    }

    @Override
    default Long insert(Account account) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO account (userId, balance, currency) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, account.getUserId());
            stmt.setBigDecimal(2, account.getBalance());
            stmt.setInt(3, account.getCurrency().getIso4217_code());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("insert(): failed to insert account " + account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("insert(): failed to insert account " + account, e);
        }
    }

    @Override
    default void update(Long id, Account account) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE account SET balance = ?, currency = ? WHERE id = ?");
            stmt.setBigDecimal(1, account.getBalance());
            stmt.setInt(2, account.getCurrency().getIso4217_code());
            stmt.setLong(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("update(): failed to update account " + id, e);
        }
    }

    @Override
    default void delete(Long id) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM account WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.getGeneratedKeys();
        } catch (SQLException e) {
            throw new RuntimeException("delete(): failed to delete account " + id, e);
        }
    }
}
