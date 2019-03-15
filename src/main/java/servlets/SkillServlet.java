package servlets;

import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.skill.SkillService;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/skills")
public class SkillServlet extends BaseServlet {
    private UserService userService = ObjectFactory.getUserService();
    private SkillService skillService = ObjectFactory.getSkillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        returnJson(skillService.getSkillsDto(), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String skillName = req.getParameter("skillName");
        try {
            userService.addSkill(skillName, userService.getCurrentUser());
            resp.sendRedirect("/skills");
        } catch (NotFoundException e) {
            showError(req, resp, "Project not fonud", HttpStatus.NOTFOUND);
        }
    }
}
