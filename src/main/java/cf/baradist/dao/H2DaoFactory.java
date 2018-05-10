package cf.baradist.dao;

import org.apache.commons.dbutils.DbUtils;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DaoFactory extends DaoFactory {
    // TODO: move to properties
    private static final String H2_DRIVER = "org.h2.Driver";
    public static final String H2_CONNECTION_URL = "jdbc:h2:mem:moneyapp;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "sa";
    private static Connection connection;

//    private final UserDao userDao = new UserDaoImpl();

    H2DaoFactory(/*Connection connection*/) {
//        setConnection(connection);
//        if (!DbUtils.loadDriver(H2_DRIVER)) {
//            throw new RuntimeException("Can't load driver: " + H2_DRIVER);
//        }
    }

    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(H2_CONNECTION_URL, H2_USER, H2_PASSWORD);
        return connection;
    }

    public static void setConnection(Connection connection) {
        H2DaoFactory.connection = connection;
    }
//    public UserDao getUserDao() {
//        return userDao;
//    }

    @Override
    public void fillTestData() {
        try (Connection conn = getConnection()) {
            RunScript.execute(conn, new FileReader("src/main/resources/init.sql"));
        } catch (SQLException e) {
            throw new RuntimeException("fillTestData(): ", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("fillTestData(): can't find a file \"src/main/resources/init.sql\"", e);
        }
    }
}
