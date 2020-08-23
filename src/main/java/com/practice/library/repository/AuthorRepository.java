package com.practice.library.repository;


import com.practice.library.entity.AuthorEntity;

public interface AuthorRepository extends BaseRepository<AuthorEntity> {
    AuthorEntity read(String name);
}
