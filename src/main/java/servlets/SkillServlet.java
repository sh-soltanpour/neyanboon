package servlets;

import dtos.ProjectSkillDto;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.skill.SkillService;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/skills")
public class SkillServlet extends BaseServlet {
    private SkillService skillService = ObjectFactory.getSkillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        returnJson(skillService.getSkillsDto(), resp);
    }


}
