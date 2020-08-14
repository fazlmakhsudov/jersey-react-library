package com.practice.library.service;


import com.practice.library.entity.Book;

public interface BookService extends BaseService<Book> {
    Book findByAuthor(String name);
}
