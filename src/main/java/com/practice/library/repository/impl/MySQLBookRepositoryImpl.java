package com.practice.library.repository.impl;

import com.practice.library.entity.Author;
import com.practice.library.entity.Book;
import com.practice.library.repository.BookRepository;
import com.practice.library.util.DBUtil;
import com.practice.library.util.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLBookRepositoryImpl implements BookRepository {
    private final DBUtil dbUtil = DBUtil.getInstance();

    @Override
    public int create(Book book) throws SQLException {
        dbUtil.connect();
        boolean rowInserted = false;
        try (PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(Queries.CREATE_BOOK.getQuery())) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getPublishDate().toString());
            statement.setInt(3, book.getAuthor().getId());
            rowInserted = statement.executeUpdate() > 0;
        }
        dbUtil.disconnect();
        System.out.println(rowInserted);
        if (rowInserted) {
            return book.getId();
        }
        return -1;
    }

    @Override
    public Book read(int id) throws SQLException {
        Book book = null;
        dbUtil.connect();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_BOOK_BY_ID.getQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    int authorId = resultSet.getInt(4);
                    book = new Book();
                    book.setId(id);
                    book.setName(name);
                    book.setPublishDate(publishDate);
                    book.setAuthor(getAuthor(authorId));
                }
            }
        }
        dbUtil.disconnect();
        return book;
    }

    @Override
    public Book read(String name) throws SQLException {
        Book book = null;
        dbUtil.connect();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_BOOK_BY_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = Integer.parseInt(resultSet.getString(1));
                    String nameOriginal = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    int authorId = resultSet.getInt(4);
                    book = new Book();
                    book.setId(id);
                    book.setName(nameOriginal);
                    book.setPublishDate(publishDate);
                    book.setAuthor(getAuthor(authorId));
                }
            }
        }
        dbUtil.disconnect();
        return book;
    }

    private Author getAuthor(int authorId) throws SQLException {
        Author author = new Author();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_AUTHOR_BY_ID.getQuery())) {
            statement.setInt(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString(2);
                    LocalDate birthdate = LocalDate.parse(resultSet.getString(3));
                    author.setId(authorId);
                    author.setName(name);
                    author.setBirthdate(birthdate);
                }
            }
        }
        return author;
    }

    @Override
    public boolean update(Book book) throws SQLException {
        dbUtil.connect();
        boolean rowUpdated = false;
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.UPDATE_BOOK.getQuery())) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getPublishDate().toString());
            statement.setInt(3, book.getAuthor().getId());
            statement.setInt(4, book.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        dbUtil.disconnect();
        return rowUpdated;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        dbUtil.connect();
        boolean rowDeleted = false;
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.DELETE_BOOK.getQuery())) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        dbUtil.disconnect();
        return rowDeleted;
    }

    @Override
    public List<Book> readAll() throws SQLException {
        dbUtil.connect();
        List<Book> bookList = new ArrayList<>();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_ALL_BOOKS.getQuery())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    int authorId = resultSet.getInt(4);
                    Book book = new Book();
                    book.setId(id);
                    book.setName(name);
                    book.setPublishDate(publishDate);
                    book.setAuthor(getAuthor(authorId));
                    bookList.add(book);
                }
            }
        }
        dbUtil.disconnect();
        return bookList;
    }
}
