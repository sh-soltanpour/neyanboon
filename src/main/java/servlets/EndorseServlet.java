package servlets;

import exceptions.BadRequestException;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.user.UserService;
import servlets.models.EndorseRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/skills/endorses")
public class EndorseServlet extends BaseServlet {

    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            EndorseRequest request = parseBody(req, EndorseRequest.class);
            userService.endorse(
                    request.getEndorsedUser(),
                    request.getSkill().getName()
            );
            returnJson(userService.getUser(request.getEndorsedUser().getId()).getSkills(), resp);
        } catch (NotFoundException e) {
            returnError(e.getMessage(), HttpStatus.NOTFOUND, resp);
        } catch (BadRequestException e) {
            returnError(e.getMessage(), HttpStatus.BAD_REQUEST, resp);
        }
    }
}
