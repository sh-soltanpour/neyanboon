package services.user;

import dtos.UserDto;
import entitites.User;
import exceptions.NotFoundException;
import exceptions.PreConditionFailedException;

import java.util.List;
import java.util.Set;

public interface UserService {
    void initialize();
    User getCurrentUser();

    User getUser(String userId) throws NotFoundException;
    UserDto getUserDto(String userId) throws NotFoundException;
    List<UserDto> getOtherUsers();
    void addSkill(String skillName, User currentUser) throws NotFoundException;
    void deleteSkill(String skillName, User currentUser) throws NotFoundException;

    void endorse(UserDto endorsedDto, String skillName) throws NotFoundException, PreConditionFailedException;

    Set<String> getEndorsedList(String endorser, String endorsed);
}
