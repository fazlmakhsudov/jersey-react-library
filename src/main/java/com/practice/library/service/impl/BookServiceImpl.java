package com.practice.library.service.impl;

import com.practice.library.entity.Book;
import com.practice.library.repository.BookRepository;
import com.practice.library.service.BookService;

import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public int add(Book item) throws SQLException {
        return bookRepository.create(item);
    }

    public Book find(int id) throws SQLException {
        return bookRepository.read(id);
    }

    public boolean save(Book item) throws SQLException {
        return bookRepository.update(item);
    }

    public boolean remove(int id) throws SQLException {
        return bookRepository.delete(id);
    }

    public List<Book> findAll() throws SQLException {
        return bookRepository.readAll();
    }
}
