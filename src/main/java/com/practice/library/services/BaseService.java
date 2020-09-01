package com.practice.library.services;

import java.util.List;

public interface BaseService<T> {

    int add(T item);

    T find(int id);

    boolean save(T item);

    boolean remove(int id);

    List<T> findAll();
}
