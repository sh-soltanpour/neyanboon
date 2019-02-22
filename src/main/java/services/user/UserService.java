package services.user;

import entitites.User;

public interface UserService {
    void initialize();
    User getCurrentUser();
}
