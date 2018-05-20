package cf.baradist;

import io.swagger.jaxrs.config.DefaultJaxrsConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyServer {

    private static final String DATA_REST_BASE_PATH = "/api";
    private static final int DATA_REST_PORT = 8080;

    public static void main(String[] args) throws Exception {
        Configurer.configureDb();
        runServer();
    }

    private static void runServer() throws Exception {
        Server jettyServer = new Server(DATA_REST_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // API
        ServletHolder apiServlet = context.addServlet(ServletContainer.class, DATA_REST_BASE_PATH + "/*");
        apiServlet.setInitOrder(1);
        apiServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES,
                "cf.baradist.controller;io.swagger.jaxrs.json;io.swagger.jaxrs.listing");

        // Setup Swagger servlet
        ServletHolder swaggerServlet = context.addServlet(DefaultJaxrsConfig.class, "/swagger-core");
        swaggerServlet.setInitOrder(2);
        swaggerServlet.setInitParameter("api.version", "v1");
        swaggerServlet.setInitParameter("swagger.api.basepath", DATA_REST_BASE_PATH);

        // Lastly, the default servlet for root content (always needed, to satisfy servlet spec)
        // It is important that this is last.
        ServletHolder holderPwd = new ServletHolder("default", new DefaultServlet());
        holderPwd.setInitParameter("resourceBase", "./src/main/resources/webapp/");
        holderPwd.setInitParameter("dirAllowed", "true");
        context.addServlet(holderPwd, "/*");

        jettyServer.setHandler(context);

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
