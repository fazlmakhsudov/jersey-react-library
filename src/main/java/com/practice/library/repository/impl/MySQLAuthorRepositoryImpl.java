package com.practice.library.repository.impl;

import com.practice.library.entity.Author;
import com.practice.library.entity.Book;
import com.practice.library.repository.AuthorRepository;
import com.practice.library.util.DBUtil;
import com.practice.library.util.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLAuthorRepositoryImpl implements AuthorRepository {
    private final DBUtil dbUtil = DBUtil.getInstance();

    @Override
    public int create(Author author) throws SQLException {
        dbUtil.connect();
        boolean rowInserted = false;
        try (PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(Queries.CREATE_AUTHOR.getQuery())) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getBirthdate().toString());
            rowInserted = statement.executeUpdate() > 0;
        }
        dbUtil.disconnect();
        if (rowInserted) {
            return author.getId();
        }
        return -1;
    }

    @Override
    public Author read(int id) throws SQLException {
        Author author = null;
        dbUtil.connect();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_AUTHOR_BY_ID.getQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString(2);
                    LocalDate birthdate = LocalDate.parse(resultSet.getString(3));
                    author = new Author();
                    author.setId(id);
                    author.setName(name);
                    author.setBirthdate(birthdate);
                }
            }
        }
        if (author == null) {
            return author;
        }
        author.setBooks(getBooks(author.getId()));
        dbUtil.disconnect();
        return author;
    }

    @Override
    public Author read(String name) throws SQLException {
        Author author = null;
        dbUtil.connect();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_AUTHOR_BY_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = Integer.parseInt(resultSet.getString(1));
                    String nameOriginal = resultSet.getString(2);
                    LocalDate birthdate = LocalDate.parse(resultSet.getString(3));
                    author = new Author();
                    author.setId(id);
                    author.setName(nameOriginal);
                    author.setBirthdate(birthdate);
                }
            }
        }
        if (author == null) {
            return author;
        }
        author.setBooks(getBooks(author.getId()));
        dbUtil.disconnect();
        return author;
    }

    private List<Book> getBooks(int authorId) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_AUTHOR_BOOKS.getQuery())) {
            statement.setInt(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    Book book = new Book();
                    book.setId(bookId);
                    book.setName(name);
                    book.setPublishDate(publishDate);
                    books.add(book);
                }
            }
        }
        return books;
    }

    @Override
    public boolean update(Author author) throws SQLException {
        dbUtil.connect();
        boolean rowUpdated = false;
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.UPDATE_AUTHOR.getQuery())) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getBirthdate().toString());
            statement.setInt(3, author.getId());
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
                .prepareStatement(Queries.DELETE_AUTHOR.getQuery())) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        dbUtil.disconnect();
        return rowDeleted;
    }

    @Override
    public List<Author> readAll() throws SQLException {
        dbUtil.connect();
        List<Author> authorList = new ArrayList<>();
        try (PreparedStatement statement = dbUtil.getJdbcConnection()
                .prepareStatement(Queries.READ_ALL_AUTHORS.getQuery())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    LocalDate birthdate = LocalDate.parse(resultSet.getString(3));
                    Author author = new Author();
                    author.setId(id);
                    author.setName(name);
                    author.setBirthdate(birthdate);
                    authorList.add(author);
                }
            }
        }
        for (Author author : authorList) {
            author.setBooks(getBooks(author.getId()));
        }
        dbUtil.disconnect();
        return authorList;
    }
}
