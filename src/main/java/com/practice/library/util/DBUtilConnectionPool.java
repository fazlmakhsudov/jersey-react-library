package com.practice.library.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.OptionalInt;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtilConnectionPool {
    private static DBUtilConnectionPool dbUtilConnectionPool;
    private final ConnectionPool connectionPool;
    private final DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger("DBUtil.class");

    private DBUtilConnectionPool(Property property) throws Exception {
        connectionPool = new ConnectionPool(property);
        OptionalInt optionalInt = OptionalInt.of(Integer.parseInt(property.getConfiguration("maxTotalConn")));
        this.dataSource = connectionPool.setUpPool(optionalInt.orElse(1));
        LOGGER.log(Level.INFO, "DataSource object has been initialized: {0}",
                new String[]{connectionPool.printDbStatus()});
        LOGGER.info("DBUtil object has been instantiated");
    }

    public static DBUtilConnectionPool getInstance(Property property) throws Exception {
        if (dbUtilConnectionPool == null) {
            dbUtilConnectionPool = new DBUtilConnectionPool(property);
        }
        return dbUtilConnectionPool;
    }

    public Connection getConnectionFromPool() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            LOGGER.severe(throwables.getMessage());
        }
        LOGGER.info(connectionPool.printDbStatus());
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            LOGGER.severe(throwables.getMessage());
        }
        LOGGER.info(connectionPool.printDbStatus());
    }
}
