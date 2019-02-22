package controllers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

abstract class BaseController {
    void writeHtmlOutput(HttpExchange httpExchange, String response, int statusCode) {
        try {
            String outputString = "<!DOCTYPE html> <html dir='ltr' lang=\"fa\"> <head> <meta charset=\"UTF-8\"> <title>Project</title> </head> <body>";
            outputString += response;
            outputString += "</body> </html>";
            httpExchange.sendResponseHeaders(statusCode, outputString.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(outputString.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
