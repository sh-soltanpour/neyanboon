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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/projects")
public class ProjectListServlet extends BaseServlet {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int pageNumber = 0;
            int pageSize = 10;
            if (req.getParameter("pageNumber") != null)
                pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
            if (req.getParameter("pageSize") != null)
                pageSize = Integer.valueOf(req.getParameter("pageSize"));
            User user = userService.getCurrentUser();
            List<ProjectDto> projects = projectService.getQualifiedProjectsPaginated(user, pageNumber, pageSize);
            returnJson(projects, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            returnError(e.getMessage(), HttpStatus.INTERNAL_SERVER, resp);
        }

    }
}
