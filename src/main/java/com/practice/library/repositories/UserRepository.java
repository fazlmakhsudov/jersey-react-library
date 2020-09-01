package com.practice.library.repositories;

import com.practice.library.repositories.entities.UserEntity;

public interface UserRepository extends BaseRepository<UserEntity> {
    UserEntity read(String name);
}
