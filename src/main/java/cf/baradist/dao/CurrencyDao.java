package cf.baradist.dao;

import cf.baradist.model.Currency;

import java.sql.SQLException;
import java.util.Optional;

public interface CurrencyDao extends Dao {
    Optional<Currency> getById(int currencyId) throws SQLException;
}
