package servlets;

import dtos.BidDto;
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

@WebServlet(urlPatterns = {"/projects/bids"})
public class BidServlet extends BaseServlet {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BidDto bidDto = parseBody(req, BidDto.class);
            projectService.addBidRequest(bidDto.getProject().getId(), userService.getCurrentUser(), bidDto.getBidAmount());
        } catch (NotFoundException e) {
            returnError("Project Not Found", HttpStatus.NOTFOUND, resp);
        } catch (AccessDeniedException e) {
            returnError("Access Denied", HttpStatus.ACCESSDENIED, resp);
        } catch (AlreadyExistsException e) {
            returnError("Bid Already Exists", HttpStatus.CONFLICT, resp);
        }
    }
}
