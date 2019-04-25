package servlets;

import dtos.UserSkillDto;
import exceptions.*;
import factory.ObjectFactory;
import services.user.UserService;
import servlets.models.EndorseRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@WebServlet("/users/skills/endorses")
public class EndorseServlet extends BaseServlet {

    private UserService userService = ObjectFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            EndorseRequest request = parseBody(req, EndorseRequest.class);
            userService.endorse(
                    request.getEndorsedUser(),
                    request.getSkill().getName()
            );
            List<UserSkillDto> userSkills = userService.getUser(request.getEndorsedUser().getId())
                    .getSkills().stream().map(UserSkillDto::of).collect(Collectors.toList());
            Set<String> endorsedSkills = userService.getEndorsedList(
                    userService.getCurrentUser().getId(),
                    request.getEndorsedUser().getId());

            userSkills.forEach(skill -> {
                skill.setEndorsed(
                        endorsedSkills.contains(skill.getName())
                );
            });
            returnJson(userSkills, resp);
        } catch (NotFoundException e) {
            returnError(e.getMessage(), HttpStatus.NOTFOUND, resp);
        } catch (BadRequestException e) {
            returnError(e.getMessage(), HttpStatus.BAD_REQUEST, resp);
        } catch (PreConditionFailedException e) {
            returnError(e.getMessage(), HttpStatus.PRECONDITION_FAILED, resp);
        } catch (AlreadyExistsException e) {
            returnError(e.getMessage(), HttpStatus.CONFLICT, resp);
        } catch (InternalErrorException e) {
            returnError(e.getMessage(), HttpStatus.INTERNAL_SERVER, resp);
        }
    }
}
