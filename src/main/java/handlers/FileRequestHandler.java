package handlers;

import Services.Register;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
            String urlPath =
                    exchange.getRequestURI().toString();
            //If urlPath is null or “/”, set urlPath to “/index.html”
            if (urlPath == null || urlPath.equals("/")) {
                urlPath = "/index.html";
            }

            String filePath = "web" + urlPath;
            File newFile = new File(filePath);

            OutputStream respBody = exchange.getResponseBody();

            if (!newFile.exists()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                File error = new File("web" + "/HTML/404.html");
                Files.copy(error.toPath(), respBody);
                respBody.close();

            } else {
                // respBody = exchange.getResponseBody();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                Files.copy(newFile.toPath(), respBody);
                respBody.close();
            }

        } else {
            // We expected a GET but got something else, so we return a "bad request"
            // status code to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }

    }
}
