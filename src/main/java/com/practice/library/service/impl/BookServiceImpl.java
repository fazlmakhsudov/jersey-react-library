package com.practice.library.service.impl;

import com.practice.library.entity.BookEntity;
import com.practice.library.repository.BookRepository;
import com.practice.library.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public int add(BookEntity item) {
        return bookRepository.create(item);
    }

    @Override
    public BookEntity find(int id) {
        return bookRepository.read(id);
    }

    @Override
    public BookEntity find(String name) {
        return bookRepository.read(name);
    }

    @Override
    public BookEntity findByAuthor(String name) {
        return bookRepository.readByAuthor(name);
    }

    @Override
    public boolean save(BookEntity item) {
        return bookRepository.update(item);
    }

    @Override
    public boolean remove(int id) {
        return bookRepository.delete(id);
    }

    @Override
    public List<BookEntity> findAll() {
        return bookRepository.readAll();
    }
}
