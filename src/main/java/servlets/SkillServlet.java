package servlets;

import factory.ObjectFactory;
import services.skill.SkillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/skills")
public class SkillServlet extends BaseServlet {
    private SkillService skillService = ObjectFactory.getSkillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            returnJson(skillService.getSkills(), resp);
        } catch (SQLException e) {
            e.printStackTrace();
            returnError(e.getMessage(), HttpStatus.INTERNAL_SERVER, resp);
        }
    }
}
