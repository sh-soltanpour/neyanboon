package repositories.project;

import entitites.Project;
import repositories.Repository;

import java.sql.SQLException;
import java.util.List;

public abstract class ProjectRepository extends Repository<Project, String> {
    public abstract List<Project> findByTitleOrDescriptionContains(String query) throws SQLException;

}
