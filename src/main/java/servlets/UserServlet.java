package servlets;

import factory.ObjectFactory;
import dtos.UserDto;
import exceptions.NotFoundException;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@WebServlet("/users/*")
public class UserServlet extends BaseServlet {
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> pathVariables = Arrays.asList(req.getPathInfo().split("/"));
        String userId = pathVariables.get(1);
        try {
            UserDto userDto = userService.getUserDto(userId);
            Set<String> endorsedSkills = userService.getEndorsedList(userService.getCurrentUser().getId(), userDto.getId());
            userDto.getSkills().forEach(skill -> {
                skill.setEndorsed(
                        endorsedSkills.contains(skill.getName())
                );
            });
            returnJson(userDto, resp);
        } catch (NotFoundException e) {
            returnError("User not found", HttpStatus.NOTFOUND, resp);
        }
    }
}
