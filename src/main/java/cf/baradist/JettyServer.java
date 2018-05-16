package cf.baradist;

import io.swagger.jaxrs.config.DefaultJaxrsConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

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
        context.setContextPath("/");

        Server jettyServer = new Server(Integer.parseInt(Configurer.getProperties().getProperty(DATA_REST_PORT, "80")));
        jettyServer.setHandler(context);

        // Setup API resources
        ServletHolder apiServlet = context.addServlet(ServletContainer.class,
                Configurer.getProperties().getProperty(DATA_REST_BASE_PATH, "/api/*"));
        apiServlet.setInitOrder(1);
        apiServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES,
                "cf.baradist.controller;io.swagger.jaxrs.json;io.swagger.jaxrs.listing");

        // Setup Swagger servlet
        ServletHolder swaggerServlet = context.addServlet(DefaultJaxrsConfig.class, "/swagger-core");
        swaggerServlet.setInitOrder(2);
        swaggerServlet.setInitParameter("api.version", "1.0.0");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
