package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import result.PersonResult;
import service.PersonService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {

                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization") && !reqHeaders.getFirst("Authorization").equals("")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    PersonService personService = new PersonService();
                    PersonResult personResult = personService.person(authToken);

                    if (personResult.getData() == null) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        personResult = new PersonResult("Error: Data is empty");
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    OutputStream responseBody = exchange.getResponseBody();

                    Gson gson = new Gson();
                    String responseData = gson.toJson(personResult);

                    writeString(responseData, responseBody);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                    PersonResult personResult = new PersonResult("Error: Invalid authorization token");
                    OutputStream responseBody = exchange.getResponseBody();
                    Gson gson = new Gson();
                    String responseData = gson.toJson(personResult);
                    writeString(responseData, responseBody);
                }
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
            exchange.getResponseBody().close();
        } catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }
}
