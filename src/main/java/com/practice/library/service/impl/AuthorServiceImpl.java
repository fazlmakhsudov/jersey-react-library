package com.practice.library.service.impl;


import com.practice.library.entity.Author;
import com.practice.library.repository.AuthorRepository;
import com.practice.library.service.AuthorService;

import java.sql.SQLException;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public int add(Author item) throws SQLException {
        return authorRepository.create(item);
    }

    public Author find(int id) throws SQLException {
        return authorRepository.read(id);
    }

    public boolean save(Author item) throws SQLException {
        return authorRepository.update(item);
    }

    public boolean remove(int id) throws SQLException {
        return authorRepository.delete(id);
    }

    public List<Author> findAll() throws SQLException {
        return authorRepository.readAll();
    }
}
