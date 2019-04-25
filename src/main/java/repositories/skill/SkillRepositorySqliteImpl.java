package repositories.skill;

import configuration.BasicConnectionPool;
import entitites.Skill;
import exceptions.NotFoundException;
import org.sqlite.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SkillRepositorySqliteImpl extends SkillRepository {

    private final List<String> columns = Arrays.asList("id", "name");

    public SkillRepositorySqliteImpl() {
        this.tableName = "skills";
        Connection connection = BasicConnectionPool.getInstance().getConnection();
        try {
            connection.prepareStatement(createTableQuery()).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BasicConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void save(Skill skill) throws SQLException {
        if (skill.getId() == null)
            skill.setId(UUID.randomUUID().toString());
        Connection connection = BasicConnectionPool.getInstance().getConnection();
        connection.prepareStatement(insertQuery(skill)).execute();
        BasicConnectionPool.getInstance().releaseConnection(connection);
    }

    @Override
    public Skill findById(String s) throws SQLException, NotFoundException {
        return null;
    }

    @Override
    public Skill toDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    private String createTableQuery() {
        String query = "create table if not exists %s\n" +
                "(\n" +
                "  id text primary key,\n" +
                "  name text unique);";
        return String.format(query, tableName);
    }

    private String insertQuery(Skill skill) {
        return String.format("replace into %s(%s) values('%s','%s')", tableName,
                StringUtils.join(columns, ","), skill.getId(), skill.getName()
        );
    }
}
