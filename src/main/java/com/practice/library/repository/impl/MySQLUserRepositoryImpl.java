package com.practice.library.repository.impl;

import com.practice.library.entity.User;
import com.practice.library.repository.UserRepository;
import com.practice.library.util.Queries;
import com.practice.library.util.jdbc.DBUtilConnectionPool;
import com.practice.library.util.jdbc.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MySQLUserRepositoryImpl implements UserRepository {
    private DBUtilConnectionPool dbUtil;
    private static final Logger LOGGER = Logger.getLogger("MySQLBookRepository");

    public MySQLUserRepositoryImpl() {
        Property property = new Property("mysql");
        try {
            dbUtil = DBUtilConnectionPool.getInstance(property);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }


    @Override
    public int create(User user) {
        Connection cn = dbUtil.getConnectionFromPool();
        boolean rowInserted = false;
        try (PreparedStatement statement = cn.prepareStatement(Queries.CREATE_USER.getQuery())) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            rowInserted = statement.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        if (rowInserted) {
            return user.getId();
        }
        return -1;
    }

    @Override
    public User read(int id) {
        User user = null;
        Connection cn = dbUtil.getConnectionFromPool();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_AUTHOR_BY_ID.getQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        if (user == null) {
            return user;
        }
        dbUtil.releaseConnection(cn);
        return user;
    }

    @Override
    public User read(String name) {
        User user = null;
        Connection cn = dbUtil.getConnectionFromPool();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_USER_BY_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String userName = resultSet.getString(2);
                    String password = resultSet.getString(3);
                    user = new User(id, userName, password);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        if (user == null) {
            return user;
        }

        dbUtil.releaseConnection(cn);
        return user;
    }

    @Override
    public List<User> readAll() {
        Connection cn = dbUtil.getConnectionFromPool();
        List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_ALL_AUTHORS.getQuery())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String password = resultSet.getString(3);
                    User user = new User(id, name, password);
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return userList;
    }

    @Override
    public boolean update(User user) {
        Connection cn = dbUtil.getConnectionFromPool();
        boolean rowUpdated = false;
        try (PreparedStatement statement = cn.prepareStatement(Queries.UPDATE_USER.getQuery())) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return rowUpdated;
    }

    @Override
    public boolean delete(int id) {
        Connection cn = dbUtil.getConnectionFromPool();
        boolean rowDeleted = false;
        try (PreparedStatement statement = cn.prepareStatement(Queries.DELETE_USER.getQuery())) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return rowDeleted;
    }
}
