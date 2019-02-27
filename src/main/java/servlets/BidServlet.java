package servlets;

import exceptions.AccessDeniedException;
import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.project.ProjectService;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bid")
public class BidServlet extends BaseServlet {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int amount = Integer.valueOf(req.getParameter("amount"));
            String projectId = req.getParameter("projectId");
            projectService.addBidRequest(projectId, userService.getCurrentUser(), amount);
            resp.sendRedirect("/project");
        } catch (NotFoundException e) {
            showError(req, resp, "project not found", HttpStatus.NOTFOUND);
        } catch (AccessDeniedException e) {
            showError(req, resp, e.getMessage(), HttpStatus.NOTFOUND);
        } catch (AlreadyExistsException e) {
            showError(req, resp, e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
