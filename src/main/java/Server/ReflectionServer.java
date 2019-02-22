package Server;

import Factory.ObjectFactory;
import com.sun.net.httpserver.HttpServer;
import controllers.ProjectController;
import controllers.UserController;

import java.net.InetSocketAddress;

public class ReflectionServer {
    public void startServer() throws Exception {
        initialize();
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/project", new ProjectController());
        server.createContext("/user", new UserController());
        server.setExecutor(null);
        server.start();
    }

    private void initialize() {
        ObjectFactory.getProjectService().initialFetch();
        ObjectFactory.getUserService().initialize();
    }
}
