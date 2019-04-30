package servlets;

import dtos.ProjectDto;
import dtos.UserDto;
import factory.ObjectFactory;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/users/search")
public class UserSearchServlet extends BaseServlet {
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String query = req.getParameter("q");
            if (query == null)
                returnError("Query parameter is null", HttpStatus.BAD_REQUEST, resp);
            returnJson(
                    userService.search(query).stream().map(UserDto::of).collect(Collectors.toList()),
                    resp
            );
        } catch (SQLException e) {
            returnError(e.getMessage(), HttpStatus.INTERNAL_SERVER, resp);
        }
    }
}
