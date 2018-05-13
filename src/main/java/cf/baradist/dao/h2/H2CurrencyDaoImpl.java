package cf.baradist.dao.h2;

import cf.baradist.dao.CurrencyDao;
import cf.baradist.model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@FunctionalInterface
public interface H2CurrencyDaoImpl extends CurrencyDao {

    String ISO4217_CODE = "iso4217_code";
    String ISO4217_SYMCODE = "iso4217_symcode";
    String NAME = "name";

    @Override
    default Optional<Currency> getById(int currencyId) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "select iso4217_code, iso4217_symcode, name FROM currency WHERE iso4217_code = ?");
            stmt.setInt(1, currencyId);
            ResultSet rs = stmt.executeQuery();
            return Optional.ofNullable(!rs.next() ? null :
                    new Currency(currencyId,
                            rs.getString(ISO4217_SYMCODE),
                            rs.getString(NAME)));
        } catch (SQLException e) {
            throw new RuntimeException("Can't read Currency with " + ISO4217_SYMCODE + " = " + currencyId, e);
        }
    }
}
