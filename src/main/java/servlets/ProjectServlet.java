package servlets;

import factory.ObjectFactory;
import dtos.ProjectDto;
import exceptions.AccessDeniedException;
import exceptions.NotFoundException;
import services.project.ProjectService;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet("/project/*")
public class ProjectServlet extends BaseServlet {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> pathVariables = Arrays.asList(req.getPathInfo().split("/"));
        String projectId = pathVariables.get(1);
        try {
            ProjectDto project = projectService.getProject(userService.getCurrentUser(), projectId);
            returnJson(project, resp);
        } catch (AccessDeniedException e) {
            returnError("Access Denied",HttpStatus.ACCESSDENIED, resp);

        } catch (NotFoundException e) {
            returnError("Not Found", HttpStatus.NOTFOUND, resp);
        }

    }
}
