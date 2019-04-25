package repositories;

import configuration.BasicConnectionPool;
import entitites.User;
import exceptions.NotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T, Id> {
    protected String tableName;

    public abstract T findById(Id id) throws SQLException, NotFoundException;

    public List<T> findAll() throws SQLException {
        ResultSet rs = execQuery(findAllQuery());
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            result.add(toDomainModel(rs));
        }
        return result;
    }

    public abstract void save(T t) throws SQLException;

    public abstract T toDomainModel(ResultSet rs) throws SQLException;

    protected ResultSet execQuery(String query) throws SQLException {
        Connection connection = BasicConnectionPool.getInstance().getConnection();
        ResultSet rs = connection.prepareStatement(query).executeQuery();
        BasicConnectionPool.getInstance().releaseConnection(connection);
        return rs;
    }

    private String findAllQuery() {
        return String.format("SELECT * from %s", tableName);
    }

}
