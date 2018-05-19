package cf.baradist.dao.h2;

import cf.baradist.dao.TransferDao;
import cf.baradist.model.Transfer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface H2TransferDaoImpl extends TransferDao {
    String ID = "id";
    String CURRENCY = "currency";
    String AMOUNT = "amount";
    String FROM_ACCOUNT_ID = "fromAccountId";
    String TO_ACCOUNT_ID = "toAccountId";

    @Override
    default List<Transfer> getAll() throws SQLException {
        List<Transfer> transfers = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, currency, amount, fromAccountId, toAccountId FROM transfer");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transfers.add(new Transfer(rs.getLong(ID),
                        rs.getInt(CURRENCY),
                        rs.getBigDecimal(AMOUNT),
                        rs.getLong(FROM_ACCOUNT_ID),
                        rs.getLong(TO_ACCOUNT_ID)));
            }
            return transfers;
        }
    }

    @Override
    default List<Transfer> getTransferByFromAccountId(Long fromAccountId) throws SQLException {
        List<Transfer> transfers = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id, currency, amount, fromAccountId, toAccountId FROM transfer WHERE fromAccountId = ?");
            stmt.setLong(1, fromAccountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transfers.add(new Transfer(rs.getLong(ID),
                        rs.getInt(CURRENCY),
                        rs.getBigDecimal(AMOUNT),
                        rs.getLong(FROM_ACCOUNT_ID),
                        rs.getLong(TO_ACCOUNT_ID)));
            }
            return transfers;
        }
    }

    @Override
    default List<Transfer> getTransferByToAccountId(Long toAccountId) throws SQLException {
        List<Transfer> transfers = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id, currency, amount, fromAccountId, toAccountId FROM transfer WHERE toAccountId = ?");
            stmt.setLong(1, toAccountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transfers.add(new Transfer(rs.getLong(ID),
                        rs.getInt(CURRENCY),
                        rs.getBigDecimal(AMOUNT),
                        rs.getLong(FROM_ACCOUNT_ID),
                        rs.getLong(TO_ACCOUNT_ID)));
            }
            return transfers;
        }
    }

    @Override
    default List<Transfer> getTransferByFromAccountIdAndToAccountId(Long fromAccountId, Long toAccountId) throws SQLException {
        List<Transfer> transfers = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id, currency, amount, fromAccountId, toAccountId " +
                            "FROM transfer WHERE fromAccountId = ? AND toAccountId = ?");
            stmt.setLong(1, fromAccountId);
            stmt.setLong(2, toAccountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transfers.add(new Transfer(rs.getLong(ID),
                        rs.getInt(CURRENCY),
                        rs.getBigDecimal(AMOUNT),
                        rs.getLong(FROM_ACCOUNT_ID),
                        rs.getLong(TO_ACCOUNT_ID)));
            }
            return transfers;
        }
    }

    @Override
    default Optional<Transfer> getById(Long id) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id, currency, amount, fromAccountId, toAccountId FROM transfer WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return Optional.ofNullable(!rs.next() ? null :
                    new Transfer(id,
                            rs.getInt(CURRENCY),
                            rs.getBigDecimal(AMOUNT),
                            rs.getLong(FROM_ACCOUNT_ID),
                            rs.getLong(TO_ACCOUNT_ID)));
        }
    }

    @Override
    default Long commit(Transfer transfer) throws SQLException {
        Long transferId;
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement stmtInsertTransaction = conn.prepareStatement(
                    "INSERT INTO transfer (currency, amount, fromAccountId, toAccountId) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmtInsertTransaction.setInt(1, transfer.getCurrencyCode());
            stmtInsertTransaction.setBigDecimal(2, transfer.getAmount());
            stmtInsertTransaction.setLong(3, transfer.getFromAccountId());
            stmtInsertTransaction.setLong(4, transfer.getToAccountId());
            stmtInsertTransaction.executeUpdate();
            ResultSet generatedKeys = stmtInsertTransaction.getGeneratedKeys();
            if (generatedKeys.next()) {
                transferId = generatedKeys.getLong(1);
            } else {
                throw new SQLException("insert(): failed to insert transfer " + transfer);
            }

            PreparedStatement stmtDecreaseFrom = conn.prepareStatement(
                    "UPDATE account SET balance = balance - ? WHERE id = ?");
            stmtDecreaseFrom.setBigDecimal(1, transfer.getAmount());
            stmtDecreaseFrom.setLong(2, transfer.getFromAccountId());
            stmtDecreaseFrom.executeUpdate();

            PreparedStatement stmtIncreaseTo = conn.prepareStatement(
                    "UPDATE account SET balance = balance + ? WHERE id = ?");
            stmtIncreaseTo.setBigDecimal(1, transfer.getAmount());
            stmtIncreaseTo.setLong(2, transfer.getToAccountId());
            stmtIncreaseTo.executeUpdate();

            conn.commit();

            return transferId;
        }
    }
}
