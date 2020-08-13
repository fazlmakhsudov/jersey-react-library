package com.practice.library.repository;

import com.practice.library.entity.Book;

import java.sql.SQLException;

public interface BookRepository extends BaseRepository<Book> {
    Book readByAuthor(String name)  throws SQLException;
}
