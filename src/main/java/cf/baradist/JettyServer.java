package cf.baradist;

import org.eclipse.jetty.server.Server;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        final Server server = new Server(8080);
        server.setHandler(new HelloHandler());
        server.start();
        server.join();
    }
}
