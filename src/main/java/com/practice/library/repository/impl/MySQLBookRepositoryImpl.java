package com.practice.library.repository.impl;

import com.practice.library.entity.Author;
import com.practice.library.entity.Book;
import com.practice.library.repository.BookRepository;
import com.practice.library.util.Queries;
import com.practice.library.util.dao.DBUtilConnectionPool;
import com.practice.library.util.dao.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MySQLBookRepositoryImpl implements BookRepository {
    private DBUtilConnectionPool dbUtil;
    private static final Logger LOGGER = Logger.getLogger("MySQLBookRepository");

    public MySQLBookRepositoryImpl() {
        Property property = new Property();
        try {
            dbUtil = DBUtilConnectionPool.getInstance(property);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public int create(Book book) {
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
        dbUtil.returnConnectionToPool(cn);
        if (rowInserted) {
            return book.getId();
        }
        return -1;
    }

    @Override
    public Book read(int id) {
        Book book = null;
        Connection cn = dbUtil.getConnectionFromPool();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_BOOK_BY_ID.getQuery())) {
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
                    book.setAuthor(getAuthor(authorId, cn));
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.returnConnectionToPool(cn);
        return book;
    }

    @Override
    public Book read(String name) {
        Book book = null;
        Connection cn = dbUtil.getConnectionFromPool();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_BOOK_BY_NAME.getQuery())) {
            statement.setString(1, '%' + name + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String nameOriginal = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    int authorId = resultSet.getInt(4);
                    book = new Book();
                    book.setId(id);
                    book.setName(nameOriginal);
                    book.setPublishDate(publishDate);
                    book.setAuthor(getAuthor(authorId, cn));
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.returnConnectionToPool(cn);
        return book;
    }

    @Override
    public Book readByAuthor(String name) {
        Book book = null;
        Connection cn = dbUtil.getConnectionFromPool();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_BOOK_BY_AUTHOR_ID.getQuery())) {
            Author author = getAuthor(name, cn);
            if (author == null) {
                return null;
            }
            statement.setInt(1, author.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String nameOriginal = resultSet.getString(2);
                    LocalDate publishDate = LocalDate.parse(resultSet.getString(3));
                    int authorId = resultSet.getInt(4);
                    book = new Book();
                    book.setId(id);
                    book.setName(nameOriginal);
                    book.setPublishDate(publishDate);
                    book.setAuthor(getAuthor(authorId, cn));
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.returnConnectionToPool(cn);
        return book;
    }

    private Author getAuthor(int authorId, Connection cn) {
        Author author = new Author();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_AUTHOR_BY_ID.getQuery())) {
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
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return author;
    }

    private Author getAuthor(String authorName, Connection cn) {
        Author author = new Author();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_AUTHOR_BY_NAME.getQuery())) {
            statement.setString(1, '%' + authorName + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    LocalDate birthdate = LocalDate.parse(resultSet.getString(3));
                    author.setId(id);
                    author.setName(name);
                    author.setBirthdate(birthdate);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return author;
    }

    @Override
    public boolean update(Book book) {
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
        dbUtil.returnConnectionToPool(cn);
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
        dbUtil.returnConnectionToPool(cn);
        return rowDeleted;
    }

    @Override
    public List<Book> readAll() {
        Connection cn = dbUtil.getConnectionFromPool();
        List<Book> bookList = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement(Queries.READ_ALL_BOOKS.getQuery())) {
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
                    book.setAuthor(getAuthor(authorId, cn));
                    bookList.add(book);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        dbUtil.returnConnectionToPool(cn);
        return bookList;
    }
}
