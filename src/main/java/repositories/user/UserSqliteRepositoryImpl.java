package repositories.user;

import configuration.BasicConnectionPool;
import configuration.ConnectionPool;
import entitites.Skill;
import entitites.User;
import entitites.UserSkill;
import exceptions.NotFoundException;
import org.sqlite.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserSqliteRepositoryImpl extends UserRepository {

    private final List<String> columns =
            Arrays.asList("id", "firstName", "lastName", "jobTitle", "profilePictureUrl", "bio");
    private ConnectionPool pool = BasicConnectionPool.getInstance();

    public UserSqliteRepositoryImpl() {
        this.tableName = "users";
        try {
            execQuery(getCreateTableQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(String id) throws SQLException, NotFoundException {
        ResultSet resultSet = execQuery(findByIdQuery(id));
        if (resultSet.isClosed())
            throw new NotFoundException();
        return toDomainModel(resultSet);
    }

    @Override
    public void save(User user) throws SQLException {
        //TODO: update user skills
        try (Connection connection = pool.getConnection()) {
            connection.prepareStatement(insertUserQuery(user)).execute();
        }
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


    private String getCreateTableQuery() {
        return "create table if not exists users\n" +
                "(\n" +
                "  id                text primary key,\n" +
                "  firstName         text,\n" +
                "  lastName          text,\n" +
                "  jobTitle          text,\n" +
                "  profilePictureUrl text,\n" +
                "  bio               text\n" +
                ");\n";

    }

    private String insertUserQuery(User user) {
        return String.format("replace into %s(%s) values('%s','%s','%s','%s','%s','%s')", tableName,
                StringUtils.join(columns, ","), user.getId(),
                user.getFirstName(), user.getLastName(), user.getJobTitle(), user.getProfilePictureUrl(), user.getBio()
        );
    }


    private String findByIdQuery(String id) {
        return String.format("SELECT * FROM %s WHERE ID = %s", tableName, id);
    }
}