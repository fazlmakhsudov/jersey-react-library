package com.practice.library.repository.impl;

import com.practice.library.entity.Author;
import com.practice.library.entity.Book;
import com.practice.library.entity.User;
import com.practice.library.repository.AuthorRepository;
import com.practice.library.repository.UserRepository;
import com.practice.library.util.DBUtil;
import com.practice.library.util.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserRepositoryImpl implements UserRepository {
    private final DBUtil dbUtil = DBUtil.getInstance();

    @Override
    public int create(User item) throws SQLException {
        return 0;
    }

    @Override
    public boolean update(User item) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public List<User> readAll() throws SQLException {
        return null;
    }

    @Override
    public User read(int id) throws SQLException {
        User user = null;
        dbUtil.connect();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_AUTHOR_BY_ID.getQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                }
            }
        }
        if (user == null) {
            return user;
        }
//        author.setBooks(getBooks(author.getId()));
        dbUtil.disconnect();
        return user;
    }

    @Override
    public User read(String name) throws SQLException {
        User user = null;
        dbUtil.connect();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_USER_BY_NAME.getQuery())) {
            statement.setString(1,'%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String userName = resultSet.getString(2);
                    String password = resultSet.getString(3);
                    user = new User(id,userName,password);
                }
            }
        }
        if (user == null) {
            return user;
        }
//        author.setBooks(getBooks(author.getId()));
        dbUtil.disconnect();
        return user;
    }


}
