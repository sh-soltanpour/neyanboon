package servlets;

import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/endorse")
public class EndorseServlet extends BaseServlet {

    private UserService userService = ObjectFactory.getUserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endorsedUserId = req.getParameter("endorsedUser");
        String skillName = req.getParameter("skillName");
        try {
            userService.endorse(userService.getCurrentUser(), userService.getUser(endorsedUserId), skillName);
        } catch (NotFoundException e) {
            showError(req, resp, "user not found", HttpStatus.NOTFOUND);
        }

    }
}
