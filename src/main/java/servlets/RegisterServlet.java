package servlets;

import dtos.UserDto;
import exceptions.AlreadyExistsException;
import exceptions.BadRequestException;
import factory.ObjectFactory;
import services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends BaseServlet {

    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDto newUser = parseBody(req, UserDto.class);
            userService.register(newUser);
        } catch (BadRequestException e) {
            returnError(e.getMessage(), HttpStatus.BAD_REQUEST, resp);
        } catch (AlreadyExistsException e) {
            returnError(e.getMessage(), HttpStatus.CONFLICT, resp);
        }
    }
}
