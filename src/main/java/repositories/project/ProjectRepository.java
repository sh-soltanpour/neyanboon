package repositories.project;

import entitites.Project;
import repositories.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public abstract class ProjectRepository extends Repository<Project, String> {
    public abstract List<Project> findByTitleOrDescriptionContains(String query) throws SQLException;

    public abstract List<Project> findByAuctionedAndDeadlineBefore(boolean auctioned, Date date) throws SQLException;
}
