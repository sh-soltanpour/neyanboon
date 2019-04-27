package repositories;

import configuration.BasicConnectionPool;
import configuration.DBCPDataSource;
import exceptions.NotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T, Id> {

    public Repository() {
        try {
            execUpdateQuery(createTableQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<T> findAll() throws SQLException {
        ResultSet rs = execQuery(findAllQuery());
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            result.add(toDomainModel(rs));
        }
        return result;
    }

    public T findById(Id id) throws SQLException, NotFoundException {
        ResultSet resultSet = execQuery(findByIdQuery(id));
        if (resultSet.isClosed())
            throw new NotFoundException();
        return toDomainModel(resultSet);
    }

    public abstract void save(T t) throws SQLException;

    public abstract T toDomainModel(ResultSet rs) throws SQLException;

    protected ResultSet execQuery(String query) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        ResultSet rs = connection.prepareStatement(query).executeQuery();
        BasicConnectionPool.getInstance().releaseConnection(connection);
        return rs;
    }

    protected void execUpdateQuery(String query) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        connection.prepareStatement(query).execute();
        BasicConnectionPool.getInstance().releaseConnection(connection);
    }

    private String findAllQuery() {
        return String.format("SELECT * from %s", getTableName());
    }

    private String findByIdQuery(Id id) {
        return String.format("SELECT * FROM %s where id = '%s", getTableName(), id);
    }

    protected abstract String createTableQuery();

    protected abstract String getTableName();

}
