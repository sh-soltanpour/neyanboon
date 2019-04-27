package repositories.project;

import configuration.BasicConnectionPool;
import entitites.Project;
import org.sqlite.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ProjectRepositoryImpl extends ProjectRepository {

    private List<String> columns = Arrays.asList("id", "title", "description", "imageUrl", "budget", "deadline");

    @Override
    public void save(Project project) throws SQLException {
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
            connection.prepareStatement(insertQuery(project)).execute();
        }
    }

    private String insertQuery(Project project) {
        return String.format("replace into %s(%s) values('%s','%s','%s','%s','%s','%s')", getTableName(),
                StringUtils.join(columns, ","),
                project.getId(), project.getTitle(), project.getDescription(), project.getImageUrl(),
                project.getBudget(),
                project.getDeadline()
        );
    }

    @Override
    public Project toDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected String createTableQuery() {
        return "create table if not exists projects  (\n" +
                "  id text primary key,\n" +
                "  title text,\n" +
                "  description text,\n" +
                "  imageUrl text,\n" +
                "  budget int,\n" +
                "  deadline datetime\n" +
                ");";
    }

    @Override
    protected String getTableName() {
        return "projects";
    }

}
