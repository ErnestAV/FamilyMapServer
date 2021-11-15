package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import result.FillResult;
import service.FillService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean isSuccessful = false;


        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Headers requiredHeaders = exchange.getRequestHeaders();
                FillService fillService = new FillService();
                FillResult fillResult = null;

                Gson gson = new Gson();
                String newURI = exchange.getRequestURI().toString();
                String responseData = "Not a valid request";

                String checkForSlash = newURI.substring(6);

                if (checkForSlash.contains("/")) {
                    int index = checkForSlash.indexOf("/");
                    int numbOfGenerations = Integer.parseInt(checkForSlash.substring(index + 1));
                    fillResult = fillService.fill(checkForSlash.substring(0, index), numbOfGenerations);
                    responseData = gson.toJson(fillResult);
                } else {
                    int defaultGens = 4;
                    fillResult = fillService.fill(checkForSlash, defaultGens);
                    responseData = gson.toJson(fillResult);
                }

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream outputResponseBody = exchange.getResponseBody();
                writeString(responseData, outputResponseBody);
                outputResponseBody.close();
                isSuccessful = true;
            }

            if (!isSuccessful) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
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
