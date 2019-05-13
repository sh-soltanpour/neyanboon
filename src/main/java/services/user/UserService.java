package services.user;

import dtos.UserDto;
import entitites.User;
import exceptions.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface UserService {
    void initialize();

    User getCurrentUser();

    void register(UserDto userDto) throws AlreadyExistsException;

    String login(String id, String password) throws SQLException, ForbiddenException;

    User getUser(String userId) throws NotFoundException, InternalErrorException;

    UserDto getUserDto(String userId) throws NotFoundException, InternalErrorException;

    List<UserDto> getOtherUsers();

    void addSkill(String skillName, User currentUser) throws NotFoundException, AlreadyExistsException, SQLException;

    void deleteSkill(String skillName, User currentUser) throws NotFoundException, SQLException;

    void endorse(UserDto endorsedDto, String skillName) throws NotFoundException, PreConditionFailedException, AlreadyExistsException, InternalErrorException, SQLException;

    Set<String> getEndorsedList(String endorser, String endorsed) throws InternalErrorException;

    List<User> search(String query) throws SQLException;
}
