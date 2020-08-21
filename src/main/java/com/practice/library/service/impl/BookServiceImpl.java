package com.practice.library.service.impl;

import com.practice.library.entity.Book;
import com.practice.library.repository.BookRepository;
import com.practice.library.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public int add(Book item) {
        return bookRepository.create(item);
    }

    @Override
    public Book find(int id) {
        return bookRepository.read(id);
    }

    @Override
    public Book find(String name) {
        return bookRepository.read(name);
    }

    @Override
    public Book findByAuthor(String name) {
        return bookRepository.readByAuthor(name);
    }

    @Override
    public boolean save(Book item) {
        return bookRepository.update(item);
    }

    @Override
    public boolean remove(int id) {
        return bookRepository.delete(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.readAll();
    }
}
