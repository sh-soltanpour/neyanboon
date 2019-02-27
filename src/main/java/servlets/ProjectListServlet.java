package servlets;

import entitites.User;
import factory.ObjectFactory;
import dtos.ProjectDto;
import services.project.ProjectService;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/project")
public class ProjectListServlet extends BaseServlet {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userService.getCurrentUser();
        List<ProjectDto> projects = projectService.getQualifiedProjects(user);
        HashMap<String, Boolean> bidRequested = new HashMap<>();
        for (ProjectDto project : projects) {
            bidRequested.put(project.getId(), projectService.bidRequested(user.getId(), project.getId()));
        }
        req.setAttribute("projects", projects);
        req.setAttribute("bidRequested", bidRequested);
        req.getRequestDispatcher("/projects.jsp").forward(req, resp);
    }
}
