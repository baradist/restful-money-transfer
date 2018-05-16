package cf.baradist;

import cf.baradist.dao.AccountDao;
import cf.baradist.dao.DaoHandler;
import cf.baradist.dao.TransferDao;
import cf.baradist.dao.UserDao;
import cf.baradist.model.Account;
import cf.baradist.model.Transfer;
import cf.baradist.model.User;
import cf.baradist.service.AccountService;
import cf.baradist.service.TransferService;
import cf.baradist.service.UserService;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Configurer {
    private static final String INIT_SQL = "src/integrationTest/resources/init.sql";
    private static final String DS_URL = "datasource.url";
    private static final String DS_USER = "datasource.username";
    private static final String DS_PASSWORD = "datasource.password";

    private static Properties properties;

    public static void configureDb() {
        if (properties == null) {
            System.out.println("Call Configurer.readProperties first");
            return;
        }
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(properties.getProperty(DS_URL, "jdbc:h2:mem:moneytransfer;DB_CLOSE_DELAY=-1"));
        ds.setUser(properties.getProperty(DS_USER, "sa"));
        ds.setPassword(properties.getProperty(DS_PASSWORD, "sa"));
        DaoHandler.initDaos(ds);
        UserService.getInstance().setUserDao((UserDao) DaoHandler.getDaoByClass(User.class));
        AccountService.getInstance().setAccountDao((AccountDao) DaoHandler.getDaoByClass(Account.class));
        TransferService.getInstance().setTransferDao((TransferDao) DaoHandler.getDaoByClass(Transfer.class));
        TransferService.getInstance().setAccountDao((AccountDao) DaoHandler.getDaoByClass(Account.class));

        fillTestData(ds);
    }

    public static void readProperties(String propertiesFileName) {
        FileInputStream fis;
        properties = new Properties();

        try {
            fis = new FileInputStream(propertiesFileName);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Can't find a file " + propertiesFileName, e);
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    private static void fillTestData(JdbcDataSource ds) {
        try (Connection conn = ds.getConnection()) {
            RunScript.execute(conn, new FileReader(INIT_SQL));
        } catch (SQLException e) {
            throw new RuntimeException("fillTestData(): ", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("fillTestData(): can't find a file " + INIT_SQL, e);
        }
    }

}
