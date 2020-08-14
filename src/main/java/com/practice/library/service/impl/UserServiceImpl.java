package com.practice.library.service.impl;

import com.practice.library.entity.User;
import com.practice.library.repository.UserRepository;
import com.practice.library.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int add(User item) {
        return 0;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public User find(String name) {
        return userRepository.read(name);
    }

    @Override
    public boolean save(User item) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
