package repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecResponse {

    private Connection connection;
    private ResultSet resultSet;

    private QueryExecResponse(Connection connection, ResultSet resultSet) {
        this.resultSet = resultSet;
        this.connection = connection;
    }

    public static QueryExecResponse of(Connection connection, ResultSet resultSet) {
        return new QueryExecResponse(connection, resultSet);
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void close() throws SQLException {
        connection.close();
        resultSet.close();
    }
}
