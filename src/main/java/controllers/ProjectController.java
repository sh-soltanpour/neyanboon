package controllers;

import factory.ObjectFactory;
import com.sun.net.httpserver.HttpExchange;
import dtos.ProjectDto;
import exceptions.AccessDeniedException;
import exceptions.NotFoundException;
import services.project.ProjectService;
import services.user.UserService;

import java.util.Arrays;
import java.util.List;

public class ProjectController extends BaseController {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    public void handle(HttpExchange httpExchange) {
        List<String> tokens = Arrays.asList(httpExchange.getRequestURI().getPath().split("/"));
        String projectId = tokens.get(2);
        try {
            ProjectDto project = projectService.getProject(userService.getCurrentUser(), projectId);
            writeHtmlOutput(httpExchange, projectHtmlTemplate(project), HttpStatus.OK.getCode());
        } catch (NotFoundException e) {
            writeHtmlOutput(httpExchange, "<h1>Project Not Found</h1>", HttpStatus.NOTFOUND.getCode());
        } catch (AccessDeniedException e) {
            writeHtmlOutput(httpExchange, "<h1>Access Denied</h1>", HttpStatus.ACCESSDENIED.getCode());
        }
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
}
