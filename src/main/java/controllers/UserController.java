package controllers;

import factory.ObjectFactory;
import com.sun.net.httpserver.HttpExchange;
import dtos.UserDto;
import exceptions.NotFoundException;
import services.user.UserService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UserController extends BaseController {
    private UserService userService = ObjectFactory.getUserService();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        List<String> tokens = Arrays.asList(httpExchange.getRequestURI().getPath().split("/"));
        showUser(httpExchange, tokens);
    }

    private void showUser(HttpExchange httpExchange, List<String> tokens) {
        String userId = tokens.get(2);
        try {
            String response = userHtmlTemplate(userService.getUserDto(userId));
            writeHtmlOutput(httpExchange, response, HttpStatus.OK.getCode());
        } catch (NotFoundException e) {
            writeHtmlOutput(httpExchange, "<h1>User not found</h1>", HttpStatus.NOTFOUND.getCode());
        }
    }

    private String userHtmlTemplate(UserDto user) {
        return String.format("    <ul>\n" +
                "        <li>id: %s</li>\n" +
                "        <li>first name: %s</li>\n" +
                "        <li>last name: %s</li>\n" +
                "        <li>jobTitle: %s</li>\n" +
                "        <li>bio: %s</li>\n" +
                "    </ul>", user.getId(), user.getFirstName(), user.getLastName(), user.getJobTitle(), user.getBio());
    }
}
