package handlers;

import Services.PersonResult;
import Services.PersonService;
import Services.PersonsService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.AuthToken;
import model.Person;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonRequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");

                    String url = exchange.getRequestURI().toString();
                    PersonService personService;
                    PersonResult personResult;
                    PersonsService persons;

                    if(url.length() < 8){
                        persons = new PersonsService();
                        personResult = persons.getPersons(authToken);
                    }
                else {
                        url = url.substring(8);
                         personService = new PersonService();
                         personResult = personService.getPerson(url, authToken);
                    }

                    // Get the response body output stream.
                    OutputStream respBody = exchange.getResponseBody();
                    // Start sending the HTTP response to the client, starting with
                    // the status code and any defined headers.
                    String respData = ObjectConverter.serialize((personResult));
                   /* if (personResult.getMessage() == "error invalid authToken") {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                    }*/

                    if (personResult.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }

                    respBody.write(respData.getBytes()); // trying to convert string to Outputstream.

                    // Close the output stream.  This is how Java knows we are done
                    // sending data and the response is complete
                    respBody.close();

                } else {
                    // We did not get an auth token, so we return a "not authorized"
                    // status code to the client.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                }
            } else {
                // We expected a GET but got something else, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        } catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }
}
