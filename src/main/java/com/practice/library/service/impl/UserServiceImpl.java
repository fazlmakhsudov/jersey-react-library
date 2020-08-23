package com.practice.library.service.impl;

import com.practice.library.entity.UserEntity;
import com.practice.library.repository.UserRepository;
import com.practice.library.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int add(UserEntity item) {
        return 0;
    }

    @Override
    public UserEntity find(int id) {
        return null;
    }

    @Override
    public UserEntity find(String name) {
        return userRepository.read(name);
    }

    @Override
    public boolean save(UserEntity item) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }
}
