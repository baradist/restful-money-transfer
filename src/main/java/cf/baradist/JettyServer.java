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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        configureDb();
        runServer();
    }

    private static void configureDb() {
        JdbcDataSource ds = new JdbcDataSource();
        // TODO: move to properties
        ds.setURL("jdbc:h2:mem:moneyapp;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("sa");
        UserService.getInstance().setUserDao((UserDaoImpl) ds::getConnection);

        fillTestData(ds);
    }

    private static void runServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
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

    public static void fillTestData(JdbcDataSource ds) {
        try (Connection conn = ds.getConnection()) {
            RunScript.execute(conn, new FileReader("src/main/resources/init.sql"));
        } catch (SQLException e) {
            throw new RuntimeException("fillTestData(): ", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("fillTestData(): can't find a file \"src/main/resources/init.sql\"", e);
        }
    }
}
