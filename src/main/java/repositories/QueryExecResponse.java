package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecResponse {

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private QueryExecResponse(Connection connection, ResultSet resultSet) {
        this.resultSet = resultSet;
        this.connection = connection;
    }
    private QueryExecResponse(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement) {
        this.resultSet = resultSet;
        this.connection = connection;
        this.preparedStatement = preparedStatement;
    }

    public static QueryExecResponse of(Connection connection, ResultSet resultSet) {
        return new QueryExecResponse(connection, resultSet);
    }
    public static QueryExecResponse of(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement) {
        return new QueryExecResponse(connection, resultSet, preparedStatement);
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void close() throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
        resultSet.close();
    }
}
