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
import java.rmi.server.UID;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@WebServlet("/projects")
public class ProjectListServlet extends BaseServlet {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uid = UUID.randomUUID().toString();
        long start = System.currentTimeMillis();
        System.out.println("This uid enter to system: " + uid);

        try {
            int pageNumber = 0;
            int pageSize = 10;
            if (req.getParameter("pageNumber") != null)
                pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
            if (req.getParameter("pageSize") != null)
                pageSize = Integer.valueOf(req.getParameter("pageSize"));
            List<ProjectDto> projects = projectService.getProjectsPaginated(pageNumber, pageSize);
            returnJson(projects, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            returnError(e.getMessage(), HttpStatus.INTERNAL_SERVER, resp);
        }
        long end = System.currentTimeMillis();
        System.out.println("Uid: "+ uid +" exit, Time in project list service: " +  (end - start));
    }
}
