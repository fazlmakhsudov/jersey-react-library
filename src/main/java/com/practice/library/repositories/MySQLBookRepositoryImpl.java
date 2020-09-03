package com.practice.library.repositories;

import com.practice.library.repositories.entities.AuthorEntity;
import com.practice.library.repositories.entities.BookEntity;
import com.practice.library.util.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MySQLBookRepositoryImpl implements BookRepository {
    private DBManager dbUtil;
    private static final Logger LOGGER = Logger.getLogger("MySQLBookRepository");

    public MySQLBookRepositoryImpl() {
        try {
            dbUtil = DBManager.getInstance("mysql");
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public int create(BookEntity book) {
        Connection cn = dbUtil.getConnectionFromPool();

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

        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_BOOK_BY_ID.getQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("bookName");
                    LocalDate publishDate = LocalDate.parse(resultSet.getString("publishDate"));
                    book = BookEntity.builder()
                            .id(id)
                            .name(name)
                            .publishDate(publishDate)
                            .author(getAuthor(resultSet))
                            .build();
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

        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_BOOK_BY_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("bookId");
                    String nameOriginal = resultSet.getString("bookName");
                    LocalDate publishDate = LocalDate.parse(resultSet.getString("publishDate"));
                    book = BookEntity.builder()
                            .id(id)
                            .name(nameOriginal)
                            .publishDate(publishDate)
                            .author(getAuthor(resultSet))
                            .build();
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

        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_BOOK_BY_AUTHOR_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("bookId");
                    String nameOriginal = resultSet.getString("bookName");
                    LocalDate publishDate = LocalDate.parse(resultSet.getString("publishDate"));
                    book = BookEntity.builder()
                            .id(id)
                            .name(nameOriginal)
                            .publishDate(publishDate)
                            .author(getAuthor(resultSet))
                            .build();
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.releaseConnection(cn);
        return book;
    }

    private AuthorEntity getAuthor(ResultSet resultSet) throws SQLException {
        return AuthorEntity.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .birthdate(LocalDate.parse(resultSet.getString("birthdate")))
                .build();
    }

    @Override
    public boolean update(BookEntity book) {
        Connection cn = dbUtil.getConnectionFromPool();

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

        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_ALL_BOOKS.getQuery())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("bookId");
                    String name = resultSet.getString("bookName");
                    LocalDate publishDate = LocalDate.parse(resultSet.getString("publishDate"));
                    BookEntity book = BookEntity.builder()
                            .id(id)
                            .name(name)
                            .publishDate(publishDate)
                            .author(getAuthor(resultSet))
                            .build();
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
