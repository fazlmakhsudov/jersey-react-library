package com.practice.library.services;

import com.practice.library.repositories.BookRepository;
import com.practice.library.repositories.entities.builders.BookEntityBuilder;
import com.practice.library.services.domains.BookDomain;
import com.practice.library.services.domains.builders.BookDomainBuilder;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookDomainBuilder bookDomainBuilder;
    private final BookEntityBuilder bookEntityBuilder;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.bookDomainBuilder = new BookDomainBuilder();
        this.bookEntityBuilder = new BookEntityBuilder();
    }

    @Override
    public int add(BookDomain item) {
        return bookRepository.create(bookEntityBuilder.create(item));
    }

    @Override
    public BookDomain find(int id) {
        return bookDomainBuilder.create(bookRepository.read(id));
    }

    @Override
    public BookDomain find(String name) {
        return bookDomainBuilder.create(bookRepository.read(name));
    }

    @Override
    public BookDomain findByAuthor(String name) {
        return bookDomainBuilder.create(bookRepository.readByAuthor(name));
    }

    @Override
    public boolean save(BookDomain item) {
        return bookRepository.update(bookEntityBuilder.create(item));
    }

    @Override
    public boolean remove(int id) {
        return bookRepository.delete(id);
    }

    @Override
    public List<BookDomain> findAll() {
        return bookDomainBuilder.create(bookRepository.readAll());
    }
}
