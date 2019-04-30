package repositories.bid;

import entitites.Bid;
import repositories.Repository;

import java.sql.SQLException;

public abstract class BidRepository extends Repository<Bid, String> {
    abstract public boolean bidExists(String projectId, String userId) throws SQLException;
}
