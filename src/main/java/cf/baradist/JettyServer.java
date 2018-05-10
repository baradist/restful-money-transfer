package cf.baradist;

import cf.baradist.controller.EntryPoint;
import cf.baradist.controller.UserController;
import cf.baradist.dao.DaoFactory;
import cf.baradist.dao.H2DaoFactory;
import cf.baradist.dao.UserDao;
import cf.baradist.dao.UserDaoImpl;
import cf.baradist.service.UserService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;

import static cf.baradist.dao.H2DaoFactory.H2_CONNECTION_URL;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        initDb();
        runServer();
    }

    private static void initDb() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:moneyapp;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("sa");
        try {
            H2DaoFactory.setConnection(ds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DaoFactory daoFactory = DaoFactory.getDaoFactory();
        daoFactory.fillTestData();
//        final UserDao userDao__ = DaoFactory.getDaoFactory().getUserDao();
        UserService.getInstance().setUserDao((UserDaoImpl) ds::getConnection);
//        DaoFactory daoFactory = DaoFactory.getDaoFactory();
//        daoFactory.fillTestData();
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
}
