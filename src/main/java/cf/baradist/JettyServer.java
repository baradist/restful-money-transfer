package cf.baradist;

import cf.baradist.controller.EntryPoint;
import cf.baradist.controller.UserController;
import cf.baradist.dao.h2.UserDaoImpl;
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
    public static final String INIT_SQL = "src/main/resources/init.sql";
    public static final String DS_URL = "datasource.url";
    public static final String DS_USER = "datasource.username";
    public static final String DS_PASSWORD = "datasource.password";

    private static Properties properties;

    public static void main(String[] args) throws Exception {
        properties = readProperties();
        configureDb();
        runServer();
    }

    private static void configureDb() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(properties.getProperty(DS_URL, "jdbc:h2:mem:moneytransfer;DB_CLOSE_DELAY=-1"));
        ds.setUser(properties.getProperty(DS_USER, "sa"));
        ds.setPassword(properties.getProperty(DS_PASSWORD, "sa"));
        UserService.getInstance().setUserDao((UserDaoImpl) ds::getConnection);

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
                EntryPoint.class.getCanonicalName() + "," +
                        UserController.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    private static Properties readProperties() {
        FileInputStream fis;
        Properties props = new Properties();

        try {
            fis = new FileInputStream(PROPERTIES_FILE);
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Can't find a file " + PROPERTIES_FILE, e);
        }
        return props;
    }

    public static void fillTestData(JdbcDataSource ds) {
        try (Connection conn = ds.getConnection()) {
            RunScript.execute(conn, new FileReader(INIT_SQL));
        } catch (SQLException e) {
            throw new RuntimeException("fillTestData(): ", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("fillTestData(): can't find a file \"src/main/resources/init.sql\"", e);
        }
    }
}
