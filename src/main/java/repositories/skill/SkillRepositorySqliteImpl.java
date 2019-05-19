package repositories.skill;

import entitites.Skill;
import org.sqlite.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SkillRepositorySqliteImpl extends SkillRepository {

    private final List<String> columns = Arrays.asList("name");


    @Override
    public void save(Skill skill) throws SQLException {
        execUpdateQuery(insertQuery(skill));
    }

    @Override
    public Skill toDomainModel(ResultSet rs) throws SQLException {
        return new Skill(
                rs.getString("name")
        );
    }

    @Override
    protected String createTableQuery() {
        String query = "create table if not exists %s\n" +
                "(\n" +
                "  name VARCHAR(128) primary key);";
        return String.format(query, getTableName());
    }

    @Override
    protected String getTableName() {
        return "skills";
    }

    @Override
    protected String primaryKey() {
        return "name";
    }

    private String insertQuery(Skill skill) {
        return String.format("insert ignore into %s(%s) values('%s');", getTableName(),
                StringUtils.join(columns, ","), skill.getName()
        );
    }

}
