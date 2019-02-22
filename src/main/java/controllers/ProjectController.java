package controllers;

import Factory.ObjectFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dtos.ProjectDto;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class ProjectController implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        List<String> tokens = Arrays.asList(httpExchange.getRequestURI().getPath().split("/"));

        if (tokens.size() == 2) {
            getProjectsList(httpExchange, tokens);
        }
        if (tokens.size() == 3) {
            getProjectDetails(httpExchange, tokens);
        }
    }

    private void getProjectDetails(HttpExchange httpExchange, List<String> tokens) throws JsonProcessingException {
        String projectId = tokens.get(2);
        ProjectDto project = ObjectFactory.getProjectService().getProject(projectId);
        try {
            writeHtmlOutput(httpExchange, projectHtmlTemplate(project), 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String projectHtmlTemplate(ProjectDto project) {
        return String.format("<ul>\n" +
                "        <li>id: %s </li>\n" +
                "        <li>title: %s</li>\n" +
                "        <li>description: %s</li>\n" +
                "        <li>imageUrl: <img src=\"%s\" style=\"width: 50px; height: 50px;\"></li>\n" +
                "        <li>budget: %s</li>\n" +
                "    </ul>", project.getId(), project.getTitle(), project.getDescription(), project.getImageUrl(), project.getBudget());
    }

    private void getProjectsList(HttpExchange httpExchange, List<String> tokenizer) {
    }

    private void writeHtmlOutput(HttpExchange httpExchange, String response, int statusCode) throws Exception {
        String outputString = "<!DOCTYPE html> <html dir='ltr' lang=\"fa\"> <head> <meta charset=\"UTF-8\"> <title>Project</title> </head> <body>";
        outputString += response;
        outputString += "</body> </html>";
        httpExchange.sendResponseHeaders(statusCode, outputString.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(outputString.getBytes());
        os.close();
    }
}
