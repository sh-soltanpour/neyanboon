package configuration;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://185.166.107.169:32731/neyanboon");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setMinIdle(5);
        ds.setMaxIdle(50);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        while (true) {
            try {
                return ds.getConnection();
            } catch (Exception e) {
                System.out.println("Failed to get connection, retrying...");
                e.printStackTrace();
                sleep(3000);
            }
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    private DBCPDataSource() {
    }
}