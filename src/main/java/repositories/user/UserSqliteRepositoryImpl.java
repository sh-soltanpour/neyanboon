package repositories.user;

import configuration.BasicConnectionPool;
import configuration.ConnectionPool;
import configuration.DBCPDataSource;
import entitites.User;
import entitites.UserSkill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserSqliteRepositoryImpl implements UserRepository {

    private final String tableName = "users";
    private ConnectionPool pool = BasicConnectionPool.getInstance();

    public UserSqliteRepositoryImpl() {
        try {
            Connection connection = pool.getConnection();
            connection.createStatement().executeUpdate(getCreateTable());
            System.out.println("user table created");
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(String id) throws SQLException {
        Connection connection = pool.getConnection();
        ResultSet resultSet = connection.prepareStatement(findByIdQuery((id))).executeQuery();
        return toDomainModel(resultSet);
    }

    @Override
    public void save(User user) {

    }

    @Override
    public User toDomainModel(ResultSet rs) throws SQLException {
        return new User(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                new ArrayList<UserSkill>(), //TODO: include valid UserSkills
                rs.getString(6)
        );
    }

    private String getCreateTable() {
        return "create table if not exists users\n" +
                "(\n" +
                "  id                text,\n" +
                "  firstName         text,\n" +
                "  lastName          text,\n" +
                "  jobTitle          text,\n" +
                "  profilePictureUrl text,\n" +
                "  bio               text\n" +
                ");\n";

    }

    private String findByIdQuery(String id) {
        return String.format("SELECT * FROM %s WHERE ID = %s", tableName, id);
    }
}
