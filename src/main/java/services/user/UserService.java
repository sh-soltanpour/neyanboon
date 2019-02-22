package services.user;

import dtos.UserDto;
import entitites.User;
import exceptions.NotFoundException;

public interface UserService {
    void initialize();
    User getCurrentUser();

    UserDto getUser(String userId) throws NotFoundException;
}
