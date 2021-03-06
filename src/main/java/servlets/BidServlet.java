package servlets;

import dtos.BidDto;
import exceptions.*;
import factory.ObjectFactory;
import services.project.ProjectService;
import services.user.UserService;
import servlets.models.BidRequestedResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/projects/bids"})
public class BidServlet extends BaseServlet {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BidDto bidDto = parseBody(req, BidDto.class);
            projectService.addBidRequest(
                    bidDto.getProject().getId(), getCurrentUser(req), bidDto.getBidAmount()
            );
            returnJson(projectService.getProject(bidDto.getProject().getId()), resp);
        } catch (NotFoundException e) {
            returnError("Project Not Found", HttpStatus.NOTFOUND, resp);
        } catch (AccessDeniedException e) {
            returnError("Access Denied", HttpStatus.FORBIDDEN, resp);
        } catch (AlreadyExistsException e) {
            returnError("Bid Already Exists", HttpStatus.CONFLICT, resp);
        } catch (BadRequestException e) {
            returnError(e.getMessage(), HttpStatus.BAD_REQUEST, resp);
        } catch (SQLException e) {
            returnError(e.getMessage(), HttpStatus.INTERNAL_SERVER, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectId = req.getParameter("projectId");
        try {
            returnJson(
                    new BidRequestedResponse(
                            projectService.bidRequested(getCurrentUser(req).getId(), projectId)
                    ),
                    resp
            );
        } catch (InternalErrorException | SQLException e) {
            returnError(e.getMessage(), HttpStatus.INTERNAL_SERVER, resp);
        } catch (NotFoundException e) {
            returnError(e.getMessage(), HttpStatus.NOTFOUND, resp);
        }
    }
}
