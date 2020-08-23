package com.practice.library.service;

import com.practice.library.entity.UserEntity;

public interface UserService extends BaseService<UserEntity> {
    UserEntity find(String name);
}

