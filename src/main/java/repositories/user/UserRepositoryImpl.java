package repositories.user;

import configuration.DBCPDataSource;
import entitites.User;

import java.sql.Connection;

public class UserRepositoryImpl implements UserRepository {
    private static Connection connection = DBCPDataSource.getConnection();
    @Override
    public User findById(Integer integer) {
        return null;
    }

    @Override
    public void save(User user) {

    }
}
