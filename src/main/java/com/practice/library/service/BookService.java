package com.practice.library.service;


import com.practice.library.entity.Book;

import java.sql.SQLException;

public interface BookService extends BaseService<Book> {
    Book findByAuthor(String name) throws SQLException;
}
