package servlets;

import entitites.Skill;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.project.ProjectService;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/skills")
public class SkillServlet extends BaseServlet {
    private ProjectService projectService = ObjectFactory.getProjectService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("skills", projectService.getSkills());
        Set<String> hasSkills = userService.getCurrentUser().getSkills()
                .stream().map(Skill::getName).collect(Collectors.toSet());
        req.setAttribute("hasSkills", hasSkills);
        req.getRequestDispatcher("/skills.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String skillName = req.getParameter("skillName");
        try {
            projectService.addSkill(skillName, userService.getCurrentUser());
            resp.sendRedirect("/skills");
        } catch (NotFoundException e) {
            showError(req, resp, "Project not fonud", HttpStatus.NOTFOUND);
        }
    }
}
