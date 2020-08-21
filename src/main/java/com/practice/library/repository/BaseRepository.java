package com.practice.library.repository;


import java.util.List;

public interface BaseRepository<T> {

    int create(T item);

    T read(int id);

    boolean update(T item);

    boolean delete(int id);

    List<T> readAll();
}
