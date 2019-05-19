package repositories.project;

import entitites.Project;
import entitites.ProjectSkill;
import entitites.User;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import org.sqlite.util.StringUtils;
import repositories.QueryExecResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProjectRepositoryImpl extends ProjectRepository {

    private List<String> columns = Arrays.asList("id", "title", "description", "imageUrl", "budget", "deadline", "creationDate", "auctioned", "winner");

    public ProjectRepositoryImpl() {
        super();
        try {
            execUpdateQueryBatch(createRelationTablesQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Project project) throws SQLException {
        List<String> queries = new ArrayList<>();
        queries.add(insertQuery(project));
        queries.addAll(updateSkillsQuery(project));
        try {
            execUpdateQueryBatch(queries);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private String insertQuery(Project project) {
        String winnerId = null;
        if (project.getWinner() != null)
            winnerId = project.getWinner().getId();
        return String.format("insert ignore into %s(%s) values('%s','%s','%s','%s','%s','%d', '%d', '%d', '%s');", getTableName(),
                StringUtils.join(columns, ","),
                project.getId(), project.getTitle(), project.getDescription(), project.getImageUrl(),
                project.getBudget(),
                project.getDeadline().getTime(),
                project.getCreationDate().getTime(),
                project.isAuctioned() ? 1 : 0,
                winnerId
        );
    }

    private List<String> updateSkillsQuery(Project project) {
        List<String> result = new ArrayList<>();
        for (ProjectSkill skill : project.getSkills()) {
            String query = String.format("insert ignore into %s(%s) values('%s','%s','%s')", "project_skill",
                    "projectId, skillId, point",
                    project.getId(), skill.getName(), skill.getPoint()
            );
            result.add(query);
        }
        return result;
    }

    @Override
    public Project toDomainModel(ResultSet rs) throws SQLException {
        Project project = new Project(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("imageUrl"),
                Collections.emptyList(),
                Collections.emptyList(),
                rs.getInt("budget"),
                new Date(rs.getLong("deadline")),
                new Date(rs.getLong("creationDate")),
                rs.getBoolean("auctioned"),
                null
        );
        project.setSkills(getProjectSkills(project));
        String winnerId = rs.getString("winner");

        if (!winnerId.equals("null")) {
            project.setWinner(getWinner(winnerId));
        }
        return project;
    }

    private List<ProjectSkill> getProjectSkills(Project project) throws SQLException {
        String query = String.format("select skillId, point from project_skill where projectId = '%s'", project.getId());
        QueryExecResponse response = execQuery(query);
        ResultSet rs = response.getResultSet();
        List<ProjectSkill> projectSkills = new ArrayList<>();
        while (rs.next()) {
            projectSkills.add(
                    new ProjectSkill(
                            rs.getString("skillId"),
                            rs.getInt("point")
                    )
            );
        }
        response.close();
        return projectSkills;
    }

    private User getWinner(String userId) throws SQLException {
        if (userId == null)
            return null;
        try {
            return ObjectFactory.getUserRepository().findById(userId);
        } catch (NotFoundException e) {
            return null;
        }
    }

    @Override
    protected String createTableQuery() {
        return "create table if not exists projects\n" +
                "(\n" +
                "  id           VARCHAR(128)\n" +
                "    primary key default '',\n" +
                "  title        VARCHAR(512),\n" +
                "  description  VARCHAR(512),\n" +
                "  imageUrl     VARCHAR(512),\n" +
                "  budget       int,\n" +
                "  deadline     long,\n" +
                "  creationDate long,\n" +
                "  auctioned    integer default false,\n" +
                "  winner       VARCHAR(512)\n" +
                "    references users\n" +
                ");";
    }

    private List<String> createRelationTablesQuery() {
        String projectSkill = "create table if not exists project_skill\n" +
                "(\n" +
                "\tprojectId VARCHAR(128) null,\n" +
                "\tskillId VARCHAR(128) null,\n" +
                "\tpoint int null,\n" +
                "\tconstraint project_skill_projects_id_fk\n" +
                "\t\tforeign key (projectId) references projects (id),\n" +
                "\tconstraint project_skill_skills_name_fk\n" +
                "\t\tforeign key (skillId) references skills (name)\n" +
                ");";
        String index = "alter table project_skill\n" +
                "  add unique index project_skill_projectId_skillId_uindex (projectId, skillId)";
        String bid = "create table if not exists bid\n" +
                "(\n" +
                "  id          VARCHAR(512) primary key,\n" +
                "  biddingUser VARCHAR(512) ,\n" +
                "  projectId   VARCHAR(512),\n" +
                "  bidAmount   int,\n" +
                "  constraint fk_bid\n" +
                "  foreign key (biddingUser) references users(id),\n" +
                "  foreign key (projectId) references projects(id)\n" +
                ");";
        return Arrays.asList(projectSkill, index, bid);
    }

    @Override
    protected String getTableName() {
        return "projects";
    }

    @Override
    public List<Project> findByTitleOrDescriptionContains(String query) throws SQLException {
        String databaseQuery = String.format(
                "select * from projects where title like '%%%s%%' or description like '%%%s%%' order by creationDate desc;",
                query, query);
        QueryExecResponse response = execQuery(databaseQuery);
        List<Project> result = resultSetToList(response.getResultSet());
        response.close();
        return result;
    }

    @Override
    public List<Project> findByAuctionedAndDeadlineBefore(boolean auctioned, Date date) throws SQLException {
        String query = String.format("select * from projects where auctioned = %d and deadline < %d",
                auctioned ? 1 : 0, date.getTime());
        QueryExecResponse response = execQuery(query);
        List<Project> result = resultSetToList(response.getResultSet());
        response.close();
        return result;
    }
}
