package servlets;

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
import java.util.List;

@WebServlet("/project")
public class ProjectListServlet extends HttpServlet {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProjectDto> projects = projectService.getQualifiedProjects(userService.getCurrentUser());
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/projects.jsp").forward(req, resp);
    }
}
