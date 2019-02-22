package controllers;

import Factory.ObjectFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dtos.ProjectDto;
import services.project.ProjectService;
import services.user.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectController implements HttpHandler {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

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
        ProjectDto project = projectService.getProject(projectId);
        writeHtmlOutput(httpExchange, projectHtmlTemplate(project), 200);
    }

    private String projectHtmlTemplate(ProjectDto project) {
        return String.format("<ul>\n" +
                "        <li>id: %s </li>\n" +
                "        <li>title: %s</li>\n" +
                "        <li>description: %s</li>\n" +
                "        <li>image: <img src=\"%s\" style=\"width: 100px; height: 100px;\"></li>\n" +
                "        <li>budget: %s</li>\n" +
                "    </ul> <hr/>", project.getId(), project.getTitle(), project.getDescription(), project.getImageUrl(), project.getBudget());
    }

    private void getProjectsList(HttpExchange httpExchange, List<String> tokenizer) {
        List<ProjectDto> projects = projectService.getQualifiedProjects(userService.getCurrentUser());
        String response = projects.stream().map(this::projectHtmlTemplate).collect(Collectors.joining());
        writeHtmlOutput(httpExchange, response, 200);
    }

    private void writeHtmlOutput(HttpExchange httpExchange, String response, int statusCode) {
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
