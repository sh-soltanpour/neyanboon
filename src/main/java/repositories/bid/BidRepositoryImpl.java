package repositories.bid;

import entitites.Bid;
import entitites.Project;
import entitites.User;
import repositories.QueryExecResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BidRepositoryImpl extends BidRepository {
    @Override
    public void save(Bid bid) throws SQLException {
        execUpdateQuery(insertQuery(bid));
    }

    @Override
    public Bid toDomainModel(ResultSet rs) throws SQLException {
        User user = new User(rs.getString("biddingUser"));
        Project project = new Project(rs.getString("projectId"));
        return new Bid(user, project, rs.getInt("bidAmount"));
    }

    @Override
    protected String createTableQuery() {
        return "create table if not exists bid\n" +
                "(\n" +
                "  biddingUser text,\n" +
                "  projectId   text,\n" +
                "  bidAmount   int,\n" +
                "  foreign key (biddingUser) references users (id),\n" +
                "  foreign key (projectId) references projects (id),\n" +
                "  unique (biddingUser, projectId)\n" +
                ")";
    }

    @Override
    protected String getTableName() {
        return "bid";
    }

    @Override
    public boolean bidExists(String projectId, String userId) throws SQLException {
        String query = String.format(
                "SELECT EXISTS(SELECT 1 FROM bid WHERE biddingUser = '%s' and projectId = '%s') as result;",
                userId, projectId);
        QueryExecResponse response = execQuery(query);
        boolean result = response.getResultSet().getBoolean("result");
        response.close();
        return result;
    }

    @Override
    public List<Bid> findByProjectId(String id) throws SQLException {
        String query = String.format("select * from %s where projectId = '%s'", getTableName(), id);
        QueryExecResponse response = execQuery(query);
        List<Bid> result = resultSetToList(response.getResultSet());
        response.close();
        return result;
    }

    private String insertQuery(Bid bid) {
        return String.format("replace into %s(projectId, biddingUser, bidAmount) values('%s','%s',%d)", getTableName(),
                bid.getProject().getId(), bid.getBiddingUser().getId(), bid.getBidAmount());
    }
}
