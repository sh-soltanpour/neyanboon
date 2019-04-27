package configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicConnectionPool
        implements ConnectionPool {


    private static BasicConnectionPool instance;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 25;

    private BasicConnectionPool(List<Connection> pool) {
        this.connectionPool = pool;
    }

    public static BasicConnectionPool create(String url) throws SQLException {

        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url));
        }
        instance = new BasicConnectionPool(pool);
        return instance;
    }

    public static BasicConnectionPool getInstance() {
        return instance;
    }

    // standard constructors

    @Override
    public Connection getConnection() {
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url)
            throws SQLException {
        return DriverManager.getConnection(url);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}