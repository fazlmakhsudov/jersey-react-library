package com.practice.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtilJdbc {
    private final String jdbcURL;
    private final String jdbcUsername;
    private final String jdbcPassword;
    private Connection jdbcConnection;
    private static DBUtilJdbc dbUtilJdbc;

    {
        this.jdbcURL = "jdbc:mysql://localhost:3306/new_db";
        this.jdbcUsername = "root";
        this.jdbcPassword = "111111frost";
    }

    public static DBUtilJdbc getInstance() {
        if (dbUtilJdbc == null) {
            dbUtilJdbc = new DBUtilJdbc();
        }
        return dbUtilJdbc;
    }

    public Connection getJdbcConnection() {
        return jdbcConnection;
    }

    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
}
