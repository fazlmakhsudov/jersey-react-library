package com.practice.library.repository;

import com.practice.library.entity.UserEntity;

public interface UserRepository extends BaseRepository<UserEntity> {
    UserEntity read(String name);
}
