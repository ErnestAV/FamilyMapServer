package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean isSuccessful = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

                Gson gson = new Gson();
                Reader reader = new InputStreamReader(exchange.getRequestBody());

                LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);
                LoginService loginService = new LoginService();
                LoginResult loginResult = loginService.login(loginRequest);

                if (Boolean.FALSE.equals(loginResult.getSuccess())) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }

                Writer writer = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(loginResult, writer);

                reader.close();
                writer.close();

                isSuccessful = true;
            }
            if (!isSuccessful) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException | DataAccessException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0); //SERVER_ERROR is deprecated
            exchange.getResponseBody().close();
        }
    }
}
