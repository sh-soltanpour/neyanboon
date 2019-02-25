package servlets;

import Factory.ObjectFactory;
import dtos.UserDto;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UsersListServlet extends HttpServlet {
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> users = userService.getOtherUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/users.jsp").forward(req, resp);
    }
}
