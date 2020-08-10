package com.practice.library.service;

import java.sql.SQLException;
import java.util.List;

public interface BaseService<T> {

    int add(T item) throws SQLException;

    T find(int id) throws SQLException;

    T find(String name) throws SQLException;

    boolean save(T item) throws SQLException;

    boolean remove(int id) throws SQLException;

    List<T> findAll() throws SQLException;
}
