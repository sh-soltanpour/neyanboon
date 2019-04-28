package repositories.user;

import configuration.DBCPDataSource;
import entitites.User;
import entitites.UserSkill;
import org.sqlite.util.StringUtils;
import repositories.QueryExecResponse;

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


    public UserSqliteRepositoryImpl() {
        super();
        try {
            execUpdateQueryBatch(createRelationTablesQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void save(User user) throws SQLException {
        //TODO: update user skills
        List<String> queries = new ArrayList<String>();
        queries.add(insertUserQuery(user));
        queries.addAll(updateUserSkillsQuery(user));
        execUpdateQueryBatch(queries);
    }

    private List<String> updateUserSkillsQuery(User user) {
        return user.getSkills()
                .stream()
                .map(userSkill -> String.format("replace into %s(%s) values('%s','%s')", "user_skill",
                        "skillId, userId", userSkill.getName(), user.getId())
                ).collect(Collectors.toList());
    }

    @Override
    public User toDomainModel(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                new ArrayList<UserSkill>(), //TODO: include valid UserSkills
                rs.getString(6)
        );
        user.setSkills(getUserSkills(user));
        return user;
    }

    private List<UserSkill> getUserSkills(User user) throws SQLException {
        String query = String.format(
                "select * from user_skill where userId='%s'", user.getId()
        );
        QueryExecResponse response = execQuery(query);
        ResultSet rs = response.getResultSet();
        List<UserSkill> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new UserSkill(rs.getString("skillId")));
        }
        response.close();
        return result;
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

    private List<String> createRelationTablesQuery() {
        String userSkillTable = "create table if not exists user_skill(\n" +
                "  userId text,\n" +
                "  skillId text,\n" +
                "  constraint fk_user_skill\n" +
                "                       foreign key (userId) references users(id),\n" +
                "                       foreign key (skillId) references skills(id),\n" +
                "  UNIQUE(userId,skillId)\n" +
                ");";
        return Collections.singletonList(userSkillTable);
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
