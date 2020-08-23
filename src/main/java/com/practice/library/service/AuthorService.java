package com.practice.library.service;

import com.practice.library.entity.AuthorEntity;

public interface AuthorService extends BaseService<AuthorEntity> {
    AuthorEntity find(String name);
}

