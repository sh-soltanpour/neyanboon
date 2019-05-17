package repositories.bid;

import entitites.Bid;
import repositories.Repository;

import java.sql.SQLException;
import java.util.List;

public abstract class BidRepository extends Repository<Bid, String> {
    abstract public boolean bidExists(String projectId, String userId) throws SQLException;

    public abstract List<Bid> findByProjectId(String id) throws SQLException;

}
