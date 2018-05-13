package cf.baradist.dao;

import cf.baradist.dao.h2.H2AccountDaoImpl;
import cf.baradist.dao.h2.H2CurrencyDaoImpl;
import cf.baradist.dao.h2.H2UserDaoImpl;
import cf.baradist.model.Account;
import cf.baradist.model.Currency;
import cf.baradist.model.User;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DaoHandler {
    private static Map<Class, Dao> daoMap = new HashMap<>();

    public static void put(Class<? extends Object> clazz, Dao dao) {
        daoMap.put(clazz, dao);
    }

    public static Dao getDaoByClass(Class clazz) {
        return daoMap.get(clazz);
    }

    public static void initDaos(DataSource ds) {
        put(User.class, (H2UserDaoImpl) ds::getConnection);
        put(Account.class, (H2AccountDaoImpl) ds::getConnection);
        put(Currency.class, (H2CurrencyDaoImpl) ds::getConnection);
    }
}
