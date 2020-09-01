package com.practice.library.repositories;


import com.practice.library.repositories.entities.AuthorEntity;

public interface AuthorRepository extends BaseRepository<AuthorEntity> {
    AuthorEntity read(String name);
}
