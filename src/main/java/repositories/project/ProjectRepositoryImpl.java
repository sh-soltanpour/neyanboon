package repositories.project;

import configuration.BasicConnectionPool;
import entitites.Project;
import org.sqlite.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ProjectRepositoryImpl extends ProjectRepository {

    private List<String> columns = Arrays.asList("id", "title", "description", "imageUrl", "budget", "deadline");

    @Override
    public void save(Project project) throws SQLException {
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
            connection.prepareStatement(insertQuery(project)).execute();
            BasicConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private String insertQuery(Project project) {
        return String.format("replace into %s(%s) values('%s','%s','%s','%s','%s','%d')", getTableName(),
                StringUtils.join(columns, ","),
                project.getId(), project.getTitle(), project.getDescription(), project.getImageUrl(),
                project.getBudget(),
                project.getDeadline().getTime()
        );
    }

    @Override
    public Project toDomainModel(ResultSet rs) throws SQLException {
        return new Project(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("imageUrl"),
                Collections.emptyList(),
                Collections.emptyList(),
                rs.getInt("budget"),
                new Date(rs.getLong("deadline")),
                null
        );
    }

    @Override
    protected String createTableQuery() {
        return "create table if not exists projects  (\n" +
                "  id text primary key,\n" +
                "  title text,\n" +
                "  description text,\n" +
                "  imageUrl text,\n" +
                "  budget int,\n" +
                "  deadline int\n" +
                ");";
    }

    @Override
    protected String getTableName() {
        return "projects";
    }

}
