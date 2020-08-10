package com.practice.library.repository;


import java.sql.SQLException;
import java.util.List;

public interface BaseRepository<T> {

    int create(T item) throws SQLException;

    T read(int id) throws SQLException;

    boolean update(T item) throws SQLException;

    boolean delete(int id) throws SQLException;

    List<T> readAll() throws SQLException;
}
