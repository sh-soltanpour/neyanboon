package servlets;

import dtos.ProjectDto;
import repositories.project.ProjectRepository;
import services.project.ProjectService;
import services.project.ProjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/projects/search")
public class ProjectSearchServlet extends BaseServlet {

    private ProjectService projectService = new ProjectServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String query = req.getParameter("q");
            if (query == null)
                returnError("Query parameter is null", HttpStatus.BAD_REQUEST, resp);


            returnJson(
                    projectService.search(query).stream().map(ProjectDto::of).collect(Collectors.toList()),
                    resp
            );
        } catch (SQLException e) {
            returnError(e.getMessage(), HttpStatus.INTERNAL_SERVER, resp);
        }

    }
}
