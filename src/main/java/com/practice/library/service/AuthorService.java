package com.practice.library.service;

import com.practice.library.entity.Author;

public interface AuthorService extends BaseService<Author> {
    Author find(String name);
}

