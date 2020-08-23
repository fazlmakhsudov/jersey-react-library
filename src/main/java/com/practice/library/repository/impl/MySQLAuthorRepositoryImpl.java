package com.practice.library.repository.impl;

import com.practice.library.entity.AuthorEntity;
import com.practice.library.entity.BookEntity;
import com.practice.library.repository.AuthorRepository;
import com.practice.library.util.Queries;
import com.practice.library.util.db.DBUtilConnectionPool;
import com.practice.library.util.db.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MySQLAuthorRepositoryImpl implements AuthorRepository {
    private DBUtilConnectionPool dbUtil;
    private static final Logger LOGGER = Logger.getLogger("MySQLAuthorRepository");

    public MySQLAuthorRepositoryImpl() {
        Property property = new Property("mysql");
        try {
            dbUtil = DBUtilConnectionPool.getInstance(property);
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
                    String name = resultSet.getString(2);
                    LocalDate birthdate = LocalDate.parse(resultSet.getString(3));
                    author = new AuthorEntity();
                    author.setId(id);
                    author.setName(name);
                    author.setBirthdate(birthdate);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        if (author == null) {
            return author;
        }
        author.setBooks(getBooks(author.getId(), cn));
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
                    int id = Integer.parseInt(resultSet.getString(1));
                    String nameOriginal = resultSet.getString(2);
                    LocalDate birthdate = LocalDate.parse(resultSet.getString(3));
                    author = new AuthorEntity();
                    author.setId(id);
                    author.setName(nameOriginal);
                    author.setBirthdate(birthdate);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        if (author == null) {
            return author;
        }
        author.setBooks(getBooks(author.getId(), cn));
        dbUtil.releaseConnection(cn);
        return author;
    }

    private List<BookEntity> getBooks(int authorId, Connection cn) {
        List<BookEntity> books = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_AUTHOR_BOOKS.getQuery())) {
            statement.setInt(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    BookEntity book = new BookEntity();
                    book.setId(bookId);
                    book.setName(name);
                    book.setPublishDate(publishDate);
                    books.add(book);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return books;
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
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    LocalDate birthdate = LocalDate.parse(resultSet.getString(3));
                    AuthorEntity author = new AuthorEntity();
                    author.setId(id);
                    author.setName(name);
                    author.setBirthdate(birthdate);
                    authorList.add(author);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        for (AuthorEntity author : authorList) {
            author.setBooks(getBooks(author.getId(), cn));
        }
        dbUtil.releaseConnection(cn);
        return authorList;
    }
}
