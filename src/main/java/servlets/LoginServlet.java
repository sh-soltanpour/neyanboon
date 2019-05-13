package servlets;

import exceptions.BadRequestException;
import exceptions.ForbiddenException;
import factory.ObjectFactory;
import services.user.UserService;
import servlets.models.LoginRequest;
import servlets.models.LoginResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/users/login")
public class LoginServlet extends BaseServlet {
    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LoginRequest request = parseBody(req, LoginRequest.class);
            String token = userService.login(request.getId(), request.getPassword());
            returnJson(new LoginResponse(token), resp);
        } catch (BadRequestException e) {
            returnError(e.getMessage(), HttpStatus.BAD_REQUEST, resp);
        } catch (ForbiddenException e) {
            returnError("invalid username or password", HttpStatus.FORBIDDEN, resp);
        } catch (SQLException e) {
            returnError(e.getMessage(), HttpStatus.INTERNAL_SERVER, resp);
        }
    }
}
