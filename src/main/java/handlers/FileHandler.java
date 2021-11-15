package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Inside file handler");
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.sendResponseHeaders(405, 0);
            exchange.getResponseBody().close();
        }
        else {
            String requestURI = "web" + exchange.getRequestURI().toString();
            if (requestURI.equals("web/") || requestURI == null) {
                requestURI = "web/index.html";
            }

            File file = new File(requestURI);
            if (!file.exists()) {
                exchange.sendResponseHeaders(404, 0);
                file = new File("web/HTML/404.html");
                OutputStream responseBody = exchange.getResponseBody();
                Files.copy(file.toPath(), responseBody);
                responseBody.close();
            }
            else {
                exchange.sendResponseHeaders(200, 0);
                OutputStream responseBody = exchange.getResponseBody();
                Files.copy(file.toPath(), responseBody);
                responseBody.close();
            }
        }
    }
}
