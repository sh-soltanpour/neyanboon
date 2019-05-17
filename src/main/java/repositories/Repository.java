package repositories;

import configuration.DBCPDataSource;
import exceptions.NotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
        List<T> result = resultSetToList(rs);
        rs.close();
        connection.close();
        return result;
    }

    public List<T> findAllPaginated(int pageNumber, int pageSize, String orderBy) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        ResultSet rs = connection.prepareStatement(findAllPaginatedQuery(pageSize, pageNumber, orderBy)).executeQuery();
        List<T> result = resultSetToList(rs);
        rs.close();
        connection.close();
        return result;
    }

    protected List<T> resultSetToList(ResultSet rs) throws SQLException {
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            result.add(toDomainModel(rs));
        }
        return result;
    }

    @FunctionalInterface
    public interface FunctionWithException<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    public T findById(Id id) throws SQLException, NotFoundException {
        FunctionWithException<Connection, PreparedStatement, SQLException> createStatement = (connection) -> {
            PreparedStatement ps = connection.prepareStatement(findByIdQuery());
            ps.setString(1, id.toString());
            return ps;
        };
        QueryExecResponse response = execQuery2(createStatement);

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
        return QueryExecResponse.of(connection, rs);
    }

    protected QueryExecResponse execQuery2(
            FunctionWithException<Connection, PreparedStatement, SQLException> statementCreator) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        PreparedStatement ps = statementCreator.apply(connection);
        ResultSet rs = ps.executeQuery();
//        ResultSet rs = connection.prepareStatement(query).executeQuery();
        return QueryExecResponse.of(connection, rs, ps);
    }

    protected void execUpdateQuery(String query) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.execute();
        ps.close();
        connection.close();
    }

    protected void execUpdateQuery2(
            FunctionWithException<Connection, PreparedStatement, SQLException> statementCreator
    ) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        PreparedStatement ps = statementCreator.apply(connection);
        ps.execute();
        ps.close();
        connection.close();
    }

    protected void execUpdateQueryBatch(List<String> queries) throws SQLException {
        Connection connection = DBCPDataSource.getConnection();
        Statement statement = connection.createStatement();
        for (String query : queries)
            statement.addBatch(query);
        statement.executeBatch();
        statement.close();
        connection.close();
    }

    private String findAllQuery() {
        return String.format("SELECT * from %s", getTableName());
    }

    private String findAllPaginatedQuery(int pageSize, int pageNumber, String orderBy) {
        return String.format("SELECT * from %s order by %s desc limit %d,%d"
                , getTableName(), orderBy, pageNumber * pageSize, pageSize);
    }

    private String findByIdQuery() {
        return String.format("SELECT * FROM %s where %s = ?", getTableName(), primaryKey());
    }

    protected String primaryKey() {
        return "id";
    }

    protected abstract String createTableQuery();

    protected abstract String getTableName();

}
