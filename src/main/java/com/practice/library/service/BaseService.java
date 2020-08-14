package com.practice.library.service;

import java.util.List;

public interface BaseService<T> {

    int add(T item);

    T find(int id);

    T find(String name);

    boolean save(T item);

    boolean remove(int id);

    List<T> findAll();
}
