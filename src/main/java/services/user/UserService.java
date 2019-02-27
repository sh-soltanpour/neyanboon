package services.user;

import dtos.UserDto;
import entitites.User;
import exceptions.NotFoundException;

import java.util.List;

public interface UserService {
    void initialize();
    User getCurrentUser();

    User getUser(String userId) throws NotFoundException;
    UserDto getUserDto(String userId) throws NotFoundException;
    List<UserDto> getOtherUsers();
}
