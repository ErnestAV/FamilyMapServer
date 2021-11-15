package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

                RegisterService registerService = new RegisterService();

                Reader reader = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();

                RegisterRequest registerRequest = gson.fromJson(reader, RegisterRequest.class);
                RegisterResult registerResult = registerService.register(registerRequest);

                if (Boolean.TRUE.equals(registerResult.getSuccess())) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream responseBody = exchange.getResponseBody();
                    String responseData = gson.toJson(registerResult);

                    writeString(responseData, responseBody);
                    responseBody.close();
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String responseData = "{\"message\" : \"" + registerResult.getMessage() + "\"}";
                    OutputStream responseBody = exchange.getResponseBody();
                    writeString(responseData, responseBody);
                    responseBody.close();
                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        } catch (DataAccessException e ) {
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
