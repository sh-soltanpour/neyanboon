package repositories.user;

import configuration.DBCPDataSource;
import entitites.User;
import entitites.UserSkill;
import org.sqlite.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserSqliteRepositoryImpl extends UserRepository {

    private final List<String> columns =
            Arrays.asList("id", "firstName", "lastName", "jobTitle", "profilePictureUrl", "bio");


    @Override
    public void save(User user) throws SQLException {
        //TODO: update user skills
        try (Connection connection = DBCPDataSource.getConnection()) {
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

    @Override
    protected String createTableQuery() {
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

    @Override
    protected String getTableName() {
        return "users";
    }

    private String insertUserQuery(User user) {
        return String.format("replace into %s(%s) values('%s','%s','%s','%s','%s','%s')", getTableName(),
                StringUtils.join(columns, ","), user.getId(),
                user.getFirstName(), user.getLastName(), user.getJobTitle(), user.getProfilePictureUrl(), user.getBio()
        );
    }

}
