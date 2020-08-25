package com.practice.library.repository.impl;

import com.practice.library.entity.AuthorEntity;
import com.practice.library.entity.BookEntity;
import com.practice.library.repository.BookRepository;
import com.practice.library.util.Queries;
import com.practice.library.util.db.DBUtilConnectionPool;
import com.practice.library.util.db.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class MySQLBookRepositoryImpl implements BookRepository {
    private DBUtilConnectionPool dbUtil;
    private static final Logger LOGGER = Logger.getLogger("MySQLBookRepository");

    public MySQLBookRepositoryImpl() {
        Property property = new Property("mysql");
        try {
            dbUtil = DBUtilConnectionPool.getInstance(property);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public int create(BookEntity book) {
        Connection cn = dbUtil.getConnectionFromPool();
        if (Objects.isNull(cn)) {
            LOGGER.info("create(): No available connection");
            return -1;
        }
        boolean rowInserted = false;
        try (PreparedStatement statement = cn.prepareStatement(Queries.CREATE_BOOK.getQuery())) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getPublishDate().toString());
            statement.setInt(3, book.getAuthor().getId());
            rowInserted = statement.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        if (rowInserted) {
            return book.getId();
        }
        return -1;
    }

    @Override
    public BookEntity read(int id) {
        BookEntity book = null;
        Connection cn = dbUtil.getConnectionFromPool();
        if (Objects.isNull(cn)) {
            LOGGER.info("read(): No available connection");
            return null;
        }
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_BOOK_BY_ID.getQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    book = new BookEntity();
                    book.setId(id);
                    book.setName(name);
                    book.setPublishDate(publishDate);
                    book.setAuthor(getAuthor(resultSet));
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return book;
    }

    @Override
    public BookEntity read(String name) {
        BookEntity book = null;
        Connection cn = dbUtil.getConnectionFromPool();
        if (Objects.isNull(cn)) {
            LOGGER.info("read(): No available connection");
            return null;
        }
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_BOOK_BY_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String nameOriginal = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    book = new BookEntity();
                    book.setId(id);
                    book.setName(nameOriginal);
                    book.setPublishDate(publishDate);
                    book.setAuthor(getAuthor(resultSet));
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return book;
    }

    @Override
    public BookEntity readByAuthor(String name) {
        BookEntity book = null;
        Connection cn = dbUtil.getConnectionFromPool();
        if (Objects.isNull(cn)) {
            LOGGER.info("readByAuthor(): No available connection");
            return null;
        }
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_BOOK_BY_AUTHOR_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String nameOriginal = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    book = new BookEntity();
                    book.setId(id);
                    book.setName(nameOriginal);
                    book.setPublishDate(publishDate);
                    book.setAuthor(getAuthor(resultSet));
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return book;
    }

    private AuthorEntity getAuthor(ResultSet resultSet) throws SQLException {
        AuthorEntity author = new AuthorEntity();
        int id = resultSet.getInt(5);
        String name = resultSet.getString(6);
        LocalDate birthdate = LocalDate.parse(resultSet.getString(7));
        author.setId(id);
        author.setName(name);
        author.setBirthdate(birthdate);
        return author;
    }

    @Override
    public boolean update(BookEntity book) {
        Connection cn = dbUtil.getConnectionFromPool();
        if (Objects.isNull(cn)) {
            LOGGER.info("update(): No available connection");
            return false;
        }
        boolean rowUpdated = false;
        try (PreparedStatement statement = cn.prepareStatement(Queries.UPDATE_BOOK.getQuery())) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getPublishDate().toString());
            statement.setInt(3, book.getAuthor().getId());
            statement.setInt(4, book.getId());
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
        try (PreparedStatement statement = cn.prepareStatement(Queries.DELETE_BOOK.getQuery())) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return rowDeleted;
    }

    @Override
    public List<BookEntity> readAll() {
        Connection cn = dbUtil.getConnectionFromPool();
        List<BookEntity> bookList = new ArrayList<>();
        if (Objects.isNull(cn)) {
            LOGGER.info("readAll(): No available connection");
            return bookList;
        }
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_ALL_BOOKS.getQuery())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    BookEntity book = new BookEntity();
                    book.setId(id);
                    book.setName(name);
                    book.setPublishDate(publishDate);
                    book.setAuthor(getAuthor(resultSet));
                    bookList.add(book);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return bookList;
    }
}
