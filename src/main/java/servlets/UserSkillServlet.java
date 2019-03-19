package servlets;

import dtos.ProjectSkillDto;
import entitites.ProjectSkill;
import exceptions.BadRequestException;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/skills")
public class UserSkillServlet extends BaseServlet {

    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String skillName = parseBody(req, ProjectSkillDto.class).getName();
            userService.addSkill(skillName, userService.getCurrentUser());
            returnJson(userService.getCurrentUser().getSkills(), resp, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            returnError("Skill not found", HttpStatus.NOTFOUND, resp);
        } catch (BadRequestException e) {
            returnError(e.getMessage(), HttpStatus.BAD_REQUEST, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String skillName = parseBody(req, ProjectSkill.class).getName();
            userService.deleteSkill(skillName, userService.getCurrentUser());
            returnJson(userService.getCurrentUser().getSkills(), resp);
        } catch (NotFoundException e1) {
            returnError("Skill Not Found", HttpStatus.NOTFOUND, resp);
        } catch (BadRequestException e) {
            returnError(e.getMessage(), HttpStatus.BAD_REQUEST, resp);
        }
    }
}
