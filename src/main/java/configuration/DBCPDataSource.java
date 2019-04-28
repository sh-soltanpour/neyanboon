package configuration;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:sqlite:./neyanboon.db");
//        ds.setUsername("user");
//        ds.setPassword("password");
        ds.setMinIdle(50);
        ds.setMaxIdle(500);
        ds.setMaxOpenPreparedStatements(1000);
    }

    public static Connection getConnection() throws SQLException {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private DBCPDataSource() {
    }
}