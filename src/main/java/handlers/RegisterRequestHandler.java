package handlers;

import Services.Register;
import Services.RegisterRequest;
import Services.RegisterResult;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class RegisterRequestHandler implements HttpHandler {

    // Handles HTTP requests containing the "/routes/claim" URL path.
    // The "exchange" parameter is an HttpExchange object, which is
    // defined by Java.
    // In this context, an "exchange" is an HTTP request/response pair
    // (i.e., the client and server exchange a request and response).
    // The HttpExchange object gives the handler access to all of the
    // details of the HTTP request (Request type [GET or POST],
    // request headers, request body, etc.).
    // The HttpExchange object also gives the handler the ability
    // to construct an HTTP response and send it back to the client
    // (Status code, headers, response body, etc.).
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Register registerObject;
        // However, it does demonstrate the following:
        // 1. How to get the HTTP request type (or, "method")
        // 2. How to access HTTP request headers
        // 3. How to read JSON data from the HTTP request body
        // 4. How to return the desired status code (200, 404, etc.)
        //		in an HTTP response
        // 5. How to check an incoming HTTP request for an auth token

        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();

                    // Extract the JSON string from the HTTP request body

                    // Get the request body input stream
                    InputStream reqBody = exchange.getRequestBody();

                    // Read JSON string from the input stream
                    String reqData = readString(reqBody);

                    // Display/log the request JSON data
                    System.out.println(reqData);


                    // TODO: Claim a route based on the request data
                    RegisterRequest rr = ObjectConverter.deserialize(reqData, RegisterRequest.class);
                    registerObject = new Register();
                    RegisterResult returnValue = registerObject.register(rr);
                    String returnString = ObjectConverter.serialize(returnValue);
                    OutputStream respBody = exchange.getResponseBody();
                    System.out.println(returnValue.isSuccess());



                    // Start sending the HTTP response to the client, starting with
                    // the status code and any defined headers.
                    if (returnValue.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    respBody.write(returnString.getBytes()); // trying to convert string to Outputstream.
                    respBody.close();


            } else {
                // We expected a POST but got something else, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
            exchange.getResponseBody().close();
        } catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);

            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }


}
