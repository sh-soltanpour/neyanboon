package controllers;

import Factory.ObjectFactory;
import com.sun.net.httpserver.HttpExchange;
import dtos.ProjectDto;
import services.project.ProjectService;
import services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectsListController extends BaseController  {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    public void handle(HttpExchange httpExchange) {
        List<ProjectDto> projects = projectService.getQualifiedProjects(userService.getCurrentUser());
        String response = projects.stream().map(this::projectHtmlTemplate).collect(Collectors.joining());
        writeHtmlOutput(httpExchange, response, 200);
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
