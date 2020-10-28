package Server;

import com.sun.net.httpserver.HttpServer;
import handlers.FileRequestHandler;
import handlers.RegisterRequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FamilyMapServer {

    public static void main(String[] args)  {//try catch instead
        int port = Integer.parseInt(args[0]);  //where do I get this from? Is host not local host?
        try {
            startServer(port);
        } catch (IOException e) {
            System.out.println("FamilyMapServer not going well");
            e.printStackTrace();
        }
    }

    private static void startServer(int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(serverAddress, 10);
        registerHandlers(server);
        server.start();
        System.out.println("FamilyMapServer listening on port " + port);
    }
    private static void registerHandlers(HttpServer server) {
        server.createContext("/", new FileRequestHandler());
        server.createContext("/user/register", new RegisterRequestHandler());
    }
}

