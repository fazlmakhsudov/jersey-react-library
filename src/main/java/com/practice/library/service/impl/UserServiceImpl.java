package com.practice.library.service.impl;

import com.practice.library.entity.User;
import com.practice.library.repository.UserRepository;
import com.practice.library.service.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int add(User item) throws SQLException {
        return 0;
    }

    @Override
    public User find(int id) throws SQLException {
        return null;
    }

    @Override
    public User find(String name) throws SQLException {
        return userRepository.read(name);
    }

    @Override
    public boolean save(User item) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        return false;
    }

    @Override
    public List<User> findAll() throws SQLException {
        return null;
    }
}
