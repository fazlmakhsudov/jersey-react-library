package com.practice.library.repository.impl;

import com.practice.library.entity.UserEntity;
import com.practice.library.repository.UserRepository;
import com.practice.library.util.Queries;
import com.practice.library.util.db.DBUtilConnectionPool;
import com.practice.library.util.db.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public int create(UserEntity user) {
        Connection cn = dbUtil.getConnectionFromPool();
        if (Objects.isNull(cn)) {
            LOGGER.info("create(): No available connection");
            return -1;
        }
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
    public UserEntity read(int id) {
        UserEntity user = null;
        Connection cn = dbUtil.getConnectionFromPool();
        if (Objects.isNull(cn)) {
            LOGGER.info("read(): No available connection");
            return null;
        }
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_AUTHOR_BY_ID.getQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String userName = resultSet.getString(2);
                    String password = resultSet.getString(3);
                    user = new UserEntity(id, userName, password);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return user;
    }

    @Override
    public UserEntity read(String name) {
        UserEntity user = null;
        Connection cn = dbUtil.getConnectionFromPool();
        if (Objects.isNull(cn)) {
            LOGGER.info("read(): No available connection");
            return null;
        }
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_USER_BY_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String userName = resultSet.getString(2);
                    String password = resultSet.getString(3);
                    user = new UserEntity(id, userName, password);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return user;
    }

    @Override
    public List<UserEntity> readAll() {
        Connection cn = dbUtil.getConnectionFromPool();
        List<UserEntity> userList = new ArrayList<>();
        if (Objects.isNull(cn)) {
            LOGGER.info("readAll(): No available connection");
            return userList;
        }
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_ALL_AUTHORS.getQuery())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String password = resultSet.getString(3);
                    UserEntity user = new UserEntity(id, name, password);
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
    public boolean update(UserEntity user) {
        Connection cn = dbUtil.getConnectionFromPool();
        if (Objects.isNull(cn)) {
            LOGGER.info("update(): No available connection");
            return false;
        }
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
        if (Objects.isNull(cn)) {
            LOGGER.info("delete(): No available connection");
            return false;
        }
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
