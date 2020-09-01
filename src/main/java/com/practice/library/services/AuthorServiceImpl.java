package com.practice.library.services;


import com.practice.library.repositories.AuthorRepository;
import com.practice.library.repositories.entities.builders.AuthorEntityBuilder;
import com.practice.library.services.domains.AuthorDomain;
import com.practice.library.services.domains.builders.AuthorDomainBuilder;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorEntityBuilder authorEntityBuilder;
    private final AuthorDomainBuilder authorDomainBuilder;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.authorEntityBuilder = new AuthorEntityBuilder();
        this.authorDomainBuilder = new AuthorDomainBuilder();
    }

    @Override
    public int add(AuthorDomain item) {
        return authorRepository.create(authorEntityBuilder.create(item));
    }

    @Override
    public AuthorDomain find(int id) {
        return authorDomainBuilder.create(authorRepository.read(id));
    }

    @Override
    public AuthorDomain find(String name) {
        return authorDomainBuilder.create(authorRepository.read(name));
    }

    @Override
    public boolean save(AuthorDomain item) {
        return authorRepository.update(authorEntityBuilder.create(item));
    }

    @Override
    public boolean remove(int id) {
        return authorRepository.delete(id);
    }

    @Override
    public List<AuthorDomain> findAll() {
        return authorDomainBuilder.create(authorRepository.readAll());
    }
}
