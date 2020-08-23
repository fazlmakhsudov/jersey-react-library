package com.practice.library.service;


import com.practice.library.entity.BookEntity;

public interface BookService extends BaseService<BookEntity> {
    BookEntity findByAuthor(String name);

    BookEntity find(String name);
}
