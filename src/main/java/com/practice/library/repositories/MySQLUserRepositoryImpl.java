package com.practice.library.repositories;

import com.practice.library.repositories.entities.UserEntity;
import com.practice.library.util.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MySQLUserRepositoryImpl implements UserRepository {
    private DBManager dbUtil;
    private static final Logger LOGGER = Logger.getLogger("MySQLBookRepository");

    public MySQLUserRepositoryImpl() {
        try {
            dbUtil = DBManager.getInstance();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }


    @Override
    public int create(UserEntity user) {
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
    public UserEntity read(int id) {
        UserEntity user = null;
        Connection cn = dbUtil.getConnectionFromPool();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_AUTHOR_BY_ID.getQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = UserEntity.builder()
                            .id(id)
                            .name(resultSet.getString("name"))
                            .password(resultSet.getString("password"))
                            .build();
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
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_USER_BY_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = UserEntity.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .password(resultSet.getString("password"))
                            .build();
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
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_ALL_AUTHORS.getQuery())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserEntity user = UserEntity.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .password(resultSet.getString("password"))
                            .build();
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
