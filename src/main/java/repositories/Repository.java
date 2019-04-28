package repositories;

import configuration.DBCPDataSource;
import exceptions.NotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        Connection connection = DBCPDataSource.getConnection();
        ResultSet rs = connection.prepareStatement(findAllQuery()).executeQuery();
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            result.add(toDomainModel(rs));
        }
        rs.close();
        connection.close();
        return result;
    }

    public T findById(Id id) throws SQLException, NotFoundException {
        QueryExecResponse response = execQuery(findByIdQuery(id));
        ResultSet resultSet = response.getResultSet();
        if (resultSet.isClosed())
            throw new NotFoundException();
        T model = toDomainModel(resultSet);
        response.close();
        return model;
    }

    public abstract void save(T t) throws SQLException;

    public abstract T toDomainModel(ResultSet rs) throws SQLException;

    protected QueryExecResponse execQuery(String query) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        ResultSet rs = connection.prepareStatement(query).executeQuery();
        return QueryExecResponse.of(connection,rs);
    }

    protected void execUpdateQuery(String query) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        connection.prepareStatement(query).execute();
        connection.close();
    }

    protected void execUpdateQueryBatch(List<String> queries) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        Statement statement = connection.createStatement();
        for (String query : queries)
            statement.addBatch(query);
        statement.executeBatch();
        connection.close();
    }

    private String findAllQuery() {
        return String.format("SELECT * from %s", getTableName());
    }

    private String findByIdQuery(Id id) {
        return String.format("SELECT * FROM %s where id = '%s'", getTableName(), id);
    }

    protected abstract String createTableQuery();

    protected abstract String getTableName();

}
