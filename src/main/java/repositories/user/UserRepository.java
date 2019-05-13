package repositories.user;

import entitites.User;
import exceptions.AlreadyExistsException;
import repositories.Repository;

import java.sql.SQLException;
import java.util.List;

public abstract class UserRepository extends Repository<User, String> {
    public abstract List<User> findByNameContains(String query) throws SQLException;

    public abstract void register(User user) throws AlreadyExistsException;
}
