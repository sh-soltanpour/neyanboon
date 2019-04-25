package services.user;

import dtos.UserDto;
import entitites.User;
import exceptions.AlreadyExistsException;
import exceptions.InternalErrorException;
import exceptions.NotFoundException;
import exceptions.PreConditionFailedException;

import java.util.List;
import java.util.Set;

public interface UserService {
    void initialize();
    User getCurrentUser();

    User getUser(String userId) throws NotFoundException, InternalErrorException;
    UserDto getUserDto(String userId) throws NotFoundException, InternalErrorException;
    List<UserDto> getOtherUsers();
    void addSkill(String skillName, User currentUser) throws NotFoundException, AlreadyExistsException;
    void deleteSkill(String skillName, User currentUser) throws NotFoundException;

    void endorse(UserDto endorsedDto, String skillName) throws NotFoundException, PreConditionFailedException, AlreadyExistsException, InternalErrorException;

    Set<String> getEndorsedList(String endorser, String endorsed) throws InternalErrorException;
}
