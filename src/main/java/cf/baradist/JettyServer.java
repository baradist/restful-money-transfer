package cf.baradist;

import cf.baradist.controller.AccountController;
import cf.baradist.controller.TransferController;
import cf.baradist.controller.UserController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {

    private static final String PROPERTIES_FILE = "src/main/resources/application.properties";
    private static final String DATA_REST_BASE_PATH = "data.rest.basePath";
    private static final String DATA_REST_PORT = "data.rest.port";

    public static void main(String[] args) throws Exception {
        Configurer.readProperties(PROPERTIES_FILE);
        Configurer.configureDb();
        runServer();
    }

    private static void runServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(Configurer.getProperties().getProperty(DATA_REST_BASE_PATH, "/"));

        Server jettyServer = new Server(Integer.parseInt(Configurer.getProperties().getProperty(DATA_REST_PORT, "80")));
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                UserController.class.getCanonicalName() + "," +
                        TransferController.class.getCanonicalName() + "," +
                        AccountController.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
