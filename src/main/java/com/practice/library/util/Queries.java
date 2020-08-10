package com.practice.library.util;

import lombok.Getter;

@Getter
public enum Queries {
    CREATE_AUTHOR("INSERT INTO author (name, birthdate) VALUES (?, ?);"),
    READ_AUTHOR_BY_ID("SELECT * FROM author WHERE id = ?;"),
    READ_AUTHOR_BY_NAME("SELECT * FROM author WHERE name LIKE ?;"),
    READ_AUTHOR_BOOKS("SELECT * FROM book WHERE authorid = ?;"),
    READ_ALL_AUTHORS("SELECT * FROM author;"),
    UPDATE_AUTHOR("UPDATE author SET name = ?, birthdate = ? WHERE id = ?;"),
    DELETE_AUTHOR("DELETE FROM author where id = ?;"),
    CREATE_BOOK("INSERT INTO book (name, publishDate, authorId) VALUES (?, ?, ?);"),
    READ_BOOK_BY_ID("SELECT * FROM book WHERE id = ?;"),
    READ_BOOK_BY_NAME("SELECT * FROM book WHERE name LIKE ?;"),
    READ_ALL_BOOKS("SELECT * FROM book;"),
    UPDATE_BOOK("UPDATE book SET name = ?, publishDate = ?, authorId = ? WHERE id = ?;"),
    DELETE_BOOK("DELETE FROM book where id = ?;"),
    ;
    private final String query;

    Queries(String query) {
        this.query = query;
    }

}
