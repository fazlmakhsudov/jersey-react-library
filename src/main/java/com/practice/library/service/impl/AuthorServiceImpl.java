package com.practice.library.service.impl;


import com.practice.library.entity.Author;
import com.practice.library.repository.AuthorRepository;
import com.practice.library.service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public int add(Author item) {
        return authorRepository.create(item);
    }

    public Author find(int id) {
        return authorRepository.read(id);
    }

    @Override
    public Author find(String name) {
        return authorRepository.read(name);
    }

    public boolean save(Author item) {
        return authorRepository.update(item);
    }

    public boolean remove(int id) {
        return authorRepository.delete(id);
    }

    public List<Author> findAll() {
        return authorRepository.readAll();
    }
}
