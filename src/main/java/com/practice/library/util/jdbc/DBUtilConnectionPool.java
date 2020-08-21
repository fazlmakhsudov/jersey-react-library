package com.practice.library.util.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtilConnectionPool {
    private static DBUtilConnectionPool dbUtilConnectionPool;
    private final DataSource dataSource;
    private final Map<Connection, Boolean> mapPoolOfConnections;
    private static final Logger logger = Logger.getLogger("DBUtil.class");

    private DBUtilConnectionPool(Property property) throws Exception {
        ConnectionPool jdbcObj = new ConnectionPool(property);
        OptionalInt optionalInt = OptionalInt.of(Integer.valueOf(property.getConfiguration("maxTotalConn")));

        this.dataSource = jdbcObj.setUpPool(optionalInt.orElse(1));
        this.mapPoolOfConnections = this.initializeMapOfConnection(optionalInt.orElse(1));
        logger.log(Level.INFO, "DataSource object has been initialized: {0}",
                new String[]{jdbcObj.printDbStatus()});

        logger.info("DBUtil object has been instantiated");
    }

    private Map<Connection, Boolean> initializeMapOfConnection(int maxTotalConn) throws SQLException {
        Map<Connection, Boolean> map = new HashMap<>();
        for (int i = 0; i < maxTotalConn; i++) {
            map.put(this.dataSource.getConnection(), false);
        }
        logger.log(Level.INFO, "mapPoolOfConnections has been initialized {0}",
                new String[]{map.toString()});
        return map;
    }

    public static DBUtilConnectionPool getInstance(Property property) throws Exception {
        if (dbUtilConnectionPool == null) {
            dbUtilConnectionPool = new DBUtilConnectionPool(property);
        }
        return dbUtilConnectionPool;
    }

    public Connection getConnectionFromPool() {
        long time = System.currentTimeMillis();
        Connection connection = null;
        for (Connection conn : mapPoolOfConnections.keySet()) {
            if (!mapPoolOfConnections.get(conn)) {
                connection = conn;
                mapPoolOfConnections.replace(conn, true);
                break;
            }
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        mapPoolOfConnections.replace(connection, false);
        connection = null;
    }


    public void disconnectAllPoolConnections() throws SQLException {
        logger.info("starting disconnecting ");
        for (Connection connection : this.mapPoolOfConnections.keySet()) {
            this.mapPoolOfConnections.replace(connection, false);
            connection.close();
        }
        logger.log(Level.INFO, "{0}",
                new String[]{this.printMapPoolOfConnection()});
        logger.info("disconnect succeeded");
    }

    private String printMapPoolOfConnection() {
        StringBuilder sb = new StringBuilder();
        sb.append("Map pool of connections:\n");
        this.mapPoolOfConnections.forEach((conn, flag) -> {
            sb.append(conn + "; isUsed: " + flag + "\n");
        });
        return sb.toString();
    }
}
