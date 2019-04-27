package repositories.skill;

import configuration.BasicConnectionPool;
import entitites.Skill;
import org.sqlite.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SkillRepositorySqliteImpl extends SkillRepository {

    private final List<String> columns = Arrays.asList("id", "name");


    @Override
    public void save(Skill skill) throws SQLException {
        skill.setId(skill.getName());//TODO: change id
        Connection connection = BasicConnectionPool.getInstance().getConnection();
        connection.prepareStatement(insertQuery(skill)).execute();
        BasicConnectionPool.getInstance().releaseConnection(connection);
    }

    @Override
    public Skill toDomainModel(ResultSet rs) throws SQLException {
        return new Skill(
                rs.getString(1),
                rs.getString(2)
        );
    }

    @Override
    protected String createTableQuery() {
        String query = "create table if not exists %s\n" +
                "(\n" +
                "  id text primary key,\n" +
                "  name text unique);";
        return String.format(query, getTableName());
    }

    @Override
    protected String getTableName() {
        return "skills";
    }

    private String insertQuery(Skill skill) {
        return String.format("replace into %s(%s) values('%s','%s')", getTableName(),
                StringUtils.join(columns, ","), skill.getId(), skill.getName()
        );
    }
}
