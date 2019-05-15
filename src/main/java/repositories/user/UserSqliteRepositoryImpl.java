package repositories.user;

import entitites.User;
import entitites.UserSkill;
import exceptions.AlreadyExistsException;
import org.sqlite.util.StringUtils;
import repositories.QueryExecResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class UserSqliteRepositoryImpl extends UserRepository {

    private final List<String> columns =
            Arrays.asList("id", "firstName", "lastName", "password", "jobTitle", "profilePictureUrl", "bio");


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
        List<String> queries = new ArrayList<String>();
        queries.add(insertUserQuery(user));
        queries.addAll(updateUserSkillsQuery(user));
        execUpdateQueryBatch(queries);
    }

    private List<String> updateUserSkillsQuery(User user) {
        String commaSeperatedSkills =
                user.getSkills().stream().map(UserSkill::getName)
                        .map(name -> String.format("'%s'", name))
                        .collect(Collectors.joining(","));
        String deleteSkills = String.format("delete from user_skill where userId = '%s' and skillId not in (%s)",
                user.getId(), commaSeperatedSkills);
        List<String> queries = new ArrayList<>();
        queries.add(deleteSkills);
        queries.addAll(
                user.getSkills()
                        .stream()
                        .map(userSkill -> String.format("replace into %s(%s) values('%s','%s')", "user_skill",
                                "skillId, userId", userSkill.getName(), user.getId())
                        ).collect(Collectors.toList())
        );
        queries.addAll(updateEndorses(user));
        return queries;
    }

    private List<String> updateEndorses(User user) {
        String commaSeperatedSkills =
                user.getSkills().stream().map(UserSkill::getName)
                        .map(name -> String.format("'%s'", name))
                        .collect(Collectors.joining(","));
        String deleteEndorses = String.format("delete from endorse where endorsed = '%s' and skillId not in (%s)",
                user.getId(), commaSeperatedSkills);
        List<String> queries = new ArrayList<>();
        queries.add(deleteEndorses);
        queries.addAll(
                user.getSkills()
                        .stream()
                        .map(userSkill ->
                                userSkill.getEndorsers().stream().map(endorser ->
                                        String.format("replace into %s(%s) values('%s','%s','%s')", "endorse",
                                                "endorsed, endorser, skillId", user.getId(), endorser.getId(), userSkill.getName())
                                ).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
        );
        return queries;
    }

    @Override
    public User toDomainModel(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getString("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("password"),
                rs.getString("jobTitle"),
                rs.getString("profilePictureUrl"),
                Collections.emptyList(),
                rs.getString("bio")
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
        for (UserSkill userSkill : result) {
            userSkill.setEndorsers(getSkillEndorsers(user, userSkill));
        }
        return result;
    }

    private List<User> getSkillEndorsers(User user, UserSkill userSkill) throws SQLException {
        String query = String.format(
                "select endorser from endorse where endorsed = '%s' and skillId = '%s' ",
                user.getId(), userSkill.getName()
        );
        QueryExecResponse response = execQuery(query);
        ResultSet rs = response.getResultSet();
        List<User> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new User(rs.getString("endorser")));
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
                "  password          text,\n" +
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
                "                       foreign key (skillId) references skills(name),\n" +
                "  UNIQUE(userId,skillId)\n" +
                ");";
        String endorseTable = "create table if not exists endorse(\n" +
                "  endorser text,\n" +
                "  endorsed text,\n" +
                "  skillId text,\n" +
                "  constraint endorse_fk\n" +
                "                    foreign key (endorser) references users(id),\n" +
                "                    foreign key (endorsed) references users(id),\n" +
                "                    foreign key (skillId) references skills(name),\n" +
                "                    unique (endorsed,endorsed,skillId)\n" +
                "                    \n" +
                ");";
        return Arrays.asList(userSkillTable, endorseTable);
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    public List<User> findByNameContains(String query) throws SQLException {
        String databaseQuery = String.format("select * from users where firstName like '%%%s%%' or lastName like '%%%s%%'"
                , query, query);
        QueryExecResponse response = execQuery(databaseQuery);
        List<User> result = resultSetToList(response.getResultSet());
        response.close();
        return result;
    }

    @Override
    public void register(User user) throws AlreadyExistsException {
        String databaseQuery = String.format("insert into %s(%s) values(?,?,?,?,?,?,?)"
                , getTableName(), StringUtils.join(columns, ","));
        FunctionWithException<Connection, PreparedStatement, SQLException> statementCreator = (connection) -> {
            PreparedStatement ps = connection.prepareStatement(databaseQuery);
            ps.setString(1, user.getId());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getJobTitle());
            ps.setString(6, user.getProfilePictureUrl());
            ps.setString(7, user.getBio());
            return ps;
        };
        try {
            execUpdateQuery2(statementCreator);
        } catch (SQLException e) {
            throw new AlreadyExistsException("user already exists");
        }
    }

    private String insertUserQuery(User user) {
        return String.format("replace into %s(%s) values('%s','%s','%s','%s', '%s','%s','%s')", getTableName(),
                StringUtils.join(columns, ","), user.getId(),
                user.getFirstName(), user.getLastName(), user.getPassword(), user.getJobTitle(),
                user.getProfilePictureUrl(), user.getBio()
        );
    }

}
