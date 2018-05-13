package cf.baradist;

import cf.baradist.controller.AccountController;
import cf.baradist.controller.UserController;
import cf.baradist.dao.AccountDao;
import cf.baradist.dao.DaoHandler;
import cf.baradist.dao.UserDao;
import cf.baradist.model.Account;
import cf.baradist.model.User;
import cf.baradist.service.AccountService;
import cf.baradist.service.UserService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JettyServer {

    public static final String PROPERTIES_FILE = "src/main/resources/application.properties";
    public static final String INIT_SQL = "src/integrationTest/resources/init.sql";
    public static final String DS_URL = "datasource.url";
    public static final String DS_USER = "datasource.username";
    public static final String DS_PASSWORD = "datasource.password";

    private static Properties properties;

    public static void main(String[] args) throws Exception {
        properties = readProperties(PROPERTIES_FILE);
        configureDb();
        runServer();
    }

    private static void configureDb() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(properties.getProperty(DS_URL, "jdbc:h2:mem:moneytransfer;DB_CLOSE_DELAY=-1"));
        ds.setUser(properties.getProperty(DS_USER, "sa"));
        ds.setPassword(properties.getProperty(DS_PASSWORD, "sa"));
        DaoHandler.initDaos(ds);
        UserService.getInstance().setUserDao((UserDao) DaoHandler.getDaoByClass(User.class));
        AccountService.getInstance().setAccountDao((AccountDao) DaoHandler.getDaoByClass(Account.class));

        fillTestData(ds);
    }

    private static void runServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(properties.getProperty("data.rest.basePath", "/"));

        Server jettyServer = new Server(Integer.parseInt(properties.getProperty("data.rest.port", "80")));
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                UserController.class.getCanonicalName() + "," +
                        AccountController.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    public static Properties readProperties(String propertiesFileName) {
        FileInputStream fis;
        Properties props = new Properties();

        try {
            fis = new FileInputStream(propertiesFileName);
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Can't find a file " + propertiesFileName, e);
        }
        return props;
    }

    public static void fillTestData(JdbcDataSource ds) {
        try (Connection conn = ds.getConnection()) {
            RunScript.execute(conn, new FileReader(INIT_SQL));
        } catch (SQLException e) {
            throw new RuntimeException("fillTestData(): ", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("fillTestData(): can't find a file " + INIT_SQL, e);
        }
    }
}
