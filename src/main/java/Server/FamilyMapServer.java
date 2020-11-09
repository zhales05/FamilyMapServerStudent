package Server;

import Services.*;
import Services.jsonhandler.InfoGeneration;
import com.sun.net.httpserver.HttpServer;
import handlers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;

public class FamilyMapServer {

    public static void main(String[] args)  {
        int port = Integer.parseInt(args[0]);
        try {
            startServer(port);
        } catch (IOException e) {
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
        server.createContext("/user/login" , new LoginRequestHandler());
        server.createContext("/clear", new ClearRequestHandler());
        server.createContext("/person", new PersonRequestHandler());
        server.createContext("/load", new LoadRequestHandler());
        server.createContext("/event/", new EventRequestHandler());
        server.createContext("/event", new EventRequestHandler());
        server.createContext("/fill", new FillRequestHandler());
    }

    public static void exper() {
        //ClearRequestHandler r = new ClearRequestHandler();
       // Clear clear = new Clear();
       // clear.clearData();
       // Register r =  new Register();
       // RegisterRequest rr = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila","Parker","f");
        //r.register(rr);

        Fill f = new Fill();
        FillRequest fr = new FillRequest("/fill/sheila/2");
        f.fill(fr);
    }
}

