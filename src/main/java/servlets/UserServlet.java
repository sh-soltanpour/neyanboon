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

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> pathVariables = Arrays.asList(req.getPathInfo().split("/"));
        String userId = pathVariables.get(1);
        try {
            UserDto user = userService.getUserDto(userId);
            req.setAttribute("users", Collections.singletonList(user));
            req.setAttribute("isCurrentUser", userService.getCurrentUser().getId().equals(userId));
            req.getRequestDispatcher("/users.jsp").forward(req, resp);
        } catch (NotFoundException e) {
            resp.setStatus(404);
            req.setAttribute("message", "User not found");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
