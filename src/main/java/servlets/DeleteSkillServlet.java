package servlets;

import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.project.ProjectService;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-skill")
public class DeleteSkillServlet extends BaseServlet {

    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String skillName = req.getParameter("skillName");
        try {
            userService.deleteSkill(skillName, userService.getCurrentUser());
            resp.sendRedirect("/user/" + userService.getCurrentUser().getId());
        } catch (NotFoundException e) {
            showError(req, resp, "Project not fonud", HttpStatus.NOTFOUND);
        }
    }
}
