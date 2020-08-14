package com.practice.library.repository.impl;

import com.practice.library.entity.User;
import com.practice.library.repository.UserRepository;
import com.practice.library.util.Queries;
import com.practice.library.util.dao.DBUtilConnectionPool;
import com.practice.library.util.dao.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;

public class MySQLUserRepositoryImpl implements UserRepository {
    private DBUtilConnectionPool dbUtil;
    private static final Logger LOGGER = Logger.getLogger("MySQLBookRepository");

    public MySQLUserRepositoryImpl() {
        Property property = new Property();
        try {
            dbUtil = DBUtilConnectionPool.getInstance(property);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }


    @Override
    public int create(User item) {
        return 0;
    }

    @Override
    public boolean update(User item) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<User> readAll() {
        return null;
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
        dbUtil.returnConnectionToPool(cn);
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
        dbUtil.returnConnectionToPool(cn);
        return user;
    }
}
