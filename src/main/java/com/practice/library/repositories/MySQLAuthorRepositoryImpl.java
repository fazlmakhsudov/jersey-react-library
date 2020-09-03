package com.practice.library.repositories;

import com.practice.library.repositories.entities.AuthorEntity;
import com.practice.library.util.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MySQLAuthorRepositoryImpl implements AuthorRepository {
    private DBManager dbUtil;
    private static final Logger LOGGER = Logger.getLogger("MySQLAuthorRepository");

    public MySQLAuthorRepositoryImpl() {
        try {
            dbUtil = DBManager.getInstance("mysql");
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public int create(AuthorEntity author) {
        Connection cn = dbUtil.getConnectionFromPool();
        boolean rowInserted = false;
        try (PreparedStatement statement = cn.prepareStatement(Queries.CREATE_AUTHOR.getQuery())) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getBirthdate().toString());
            rowInserted = statement.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        if (rowInserted) {
            return author.getId();
        }
        return -1;
    }

    @Override
    public AuthorEntity read(int id) {
        AuthorEntity author = null;
        Connection cn = dbUtil.getConnectionFromPool();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_AUTHOR_BY_ID.getQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    LocalDate birthdate = LocalDate.parse(resultSet.getString("birthdate"));
                    author = AuthorEntity.builder()
                            .id(id)
                            .name(name)
                            .birthdate(birthdate)
                            .build();
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        if (author == null) {
            return null;
        }
        dbUtil.releaseConnection(cn);
        return author;
    }

    @Override
    public AuthorEntity read(String name) {
        AuthorEntity author = null;
        Connection cn = dbUtil.getConnectionFromPool();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_AUTHOR_BY_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = Integer.parseInt(resultSet.getString("id"));
                    String nameOriginal = resultSet.getString("name");
                    LocalDate birthdate = LocalDate.parse(resultSet.getString("birthdate"));
                    author = AuthorEntity.builder()
                            .id(id)
                            .name(nameOriginal)
                            .birthdate(birthdate)
                            .build();
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        if (author == null) {
            return null;
        }
        dbUtil.releaseConnection(cn);
        return author;
    }

    @Override
    public boolean update(AuthorEntity author) {
        Connection cn = dbUtil.getConnectionFromPool();
        boolean rowUpdated = false;
        try (PreparedStatement statement = cn.prepareStatement(Queries.UPDATE_AUTHOR.getQuery())) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getBirthdate().toString());
            statement.setInt(3, author.getId());
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
        try (PreparedStatement statement = cn.prepareStatement(Queries.DELETE_AUTHOR.getQuery())) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return rowDeleted;
    }

    @Override
    public List<AuthorEntity> readAll() {
        Connection cn = dbUtil.getConnectionFromPool();
        List<AuthorEntity> authorList = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_ALL_AUTHORS.getQuery())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    LocalDate birthdate = LocalDate.parse(resultSet.getString("birthdate"));
                    AuthorEntity author = AuthorEntity.builder()
                            .id(id)
                            .name(name)
                            .birthdate(birthdate)
                            .build();
                    authorList.add(author);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return authorList;
    }
}
