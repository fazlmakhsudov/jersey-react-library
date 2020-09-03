package com.practice.library.util;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.OptionalInt;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
    private static DBManager dbManager;
    private final String JDBC_DRIVER;
    private final String JDBC_DB_URL;

    // JDBC Database Credentials
    private final String JDBC_USER;
    private final String JDBC_PASSWORD;
    private static GenericObjectPool gPool = null;
    private static final Logger LOGGER = Logger.getLogger("DBUtil.class");
    private DataSource dataSource;

    private DBManager(String propertyFileName) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(propertyFileName);
        this.JDBC_DRIVER = resourceBundle.getString("jdbcDriver");
        this.JDBC_DB_URL = resourceBundle.getString("jdbcUrl") +
                "/" + resourceBundle.getString("jdbcDatabase");
        this.JDBC_USER = resourceBundle.getString("jdbcUserName");
        this.JDBC_PASSWORD = resourceBundle.getString("jdbcPassword");
        LOGGER.log(Level.INFO, "ConnectionPool has been initialised with params ({0}, {1}, {2}, {3})",
                new String[]{LOGGER.getName(), this.JDBC_DRIVER, this.JDBC_DB_URL, this.JDBC_USER, this.JDBC_PASSWORD});
        OptionalInt optionalInt = OptionalInt.of(Integer.parseInt(resourceBundle.getString("maxTotalConn")));
        this.dataSource = setUpPool(optionalInt.orElse(1));
        LOGGER.log(Level.INFO, "DataSource object has been initialized: {0}",
                new String[]{printDbStatus()});
        LOGGER.info("DBUtil object has been instantiated");
    }

    public static DBManager getInstance(String propertyFileName) throws Exception {
        if (dbManager == null) {
            dbManager = new DBManager(propertyFileName);
        }
        return dbManager;
    }

    public Connection getConnectionFromPool() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            LOGGER.severe(throwables.getMessage());
        }
        LOGGER.info(printDbStatus());
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            LOGGER.severe(throwables.getMessage());
        }
        LOGGER.info(printDbStatus());
    }

    @SuppressWarnings("unused")
    public DataSource setUpPool(int maxTotalConn) throws Exception {
        Class.forName(JDBC_DRIVER);

        // Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!
        gPool = new GenericObjectPool();
        gPool.setMaxActive(maxTotalConn);

        // Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!
        ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASSWORD);

        // Creates a PoolableConnectionFactory That Will Wraps the Connection Object Created by the ConnectionFactory to Add Object Pooling Functionality!
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
        return new PoolingDataSource(gPool);
    }

    public GenericObjectPool getConnectionPool() {
        return gPool;
    }

    // This Method Is Used To Print The Connection Pool Status
    public String printDbStatus() {
        return "{ Max.: " + getConnectionPool().getMaxActive() + "; Active: " + getConnectionPool().getNumActive() + ";" +
                " Idle: " + getConnectionPool().getNumIdle() + " }";
    }
}
