package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import result.PersonPersonIDResult;
import service.PersonPersonIDService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonPersonIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean isSuccessful = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Headers requiredHeaders = exchange.getRequestHeaders();
                if (requiredHeaders.containsKey("Authorization") && !requiredHeaders.getFirst("Authorization").equals("")) {
                    String authToken = requiredHeaders.getFirst("Authorization");
                    String requestURI = exchange.getRequestURI().toString();
                    String[] splitURI = requestURI.split("/");

                    PersonPersonIDService personPersonIDService = new PersonPersonIDService();
                    PersonPersonIDResult personPersonIDResult = personPersonIDService.personWithID(splitURI[2], authToken);

                    if (!personPersonIDResult.getSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        personPersonIDResult = new PersonPersonIDResult("Error: Success is false. Bad request");
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }

                    OutputStream responseBody = exchange.getResponseBody();

                    Gson gson = new Gson();
                    String responseData = gson.toJson(personPersonIDResult);

                    isSuccessful = true;

                    writeString(responseData, responseBody);
                }
            }
            if (!isSuccessful) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
            exchange.getResponseBody().close();
        } catch (IOException | DataAccessException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0); //SERVER ERROR is deprecated
            exchange.getResponseBody().close();
        }
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }
}
