package services.user;

import dtos.UserDto;
import entitites.User;
import exceptions.AlreadyExistsException;
import exceptions.InternalErrorException;
import exceptions.NotFoundException;
import exceptions.PreConditionFailedException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface UserService {
    void initialize();
    User getCurrentUser();

    User getUser(String userId) throws NotFoundException, InternalErrorException;
    UserDto getUserDto(String userId) throws NotFoundException, InternalErrorException;
    List<UserDto> getOtherUsers();
    void addSkill(String skillName, User currentUser) throws NotFoundException, AlreadyExistsException, SQLException;
    void deleteSkill(String skillName, User currentUser) throws NotFoundException, SQLException;

    void endorse(UserDto endorsedDto, String skillName) throws NotFoundException, PreConditionFailedException, AlreadyExistsException, InternalErrorException, SQLException;

    Set<String> getEndorsedList(String endorser, String endorsed) throws InternalErrorException;

    List<User> search(String query) throws SQLException;
}
