package com.practice.library.util;

import lombok.Getter;

@Getter
public enum Queries {
    CREATE_AUTHOR("INSERT INTO author (name, birthdate) VALUES (?, ?);"),
    READ_AUTHOR("SELECT * FROM author WHERE id = ?;"),
    READ_AUTHOR_BOOKS("SELECT * FROM book WHERE authorid = ?;"),
    READ_ALL_AUTHORS("SELECT * FROM author;"),
    UPDATE_AUTHOR("UPDATE author SET name = ?, birthdate = ? WHERE id = ?;"),
    DELETE_AUTHOR("DELETE FROM author where id = ?;"),
    CREATE_BOOK("INSERT INTO book (name, publishDate, authorId) VALUES (?, ?, ?);"),
    READ_BOOK("SELECT * FROM book WHERE id = ?;"),
    READ_ALL_BOOKS("SELECT * FROM book;"),
    UPDATE_BOOK("UPDATE book SET name = ?, publishDate = ?, authorId = ? WHERE id = ?;"),
    DELETE_BOOK("DELETE FROM book where id = ?;"),
    ;
    private final String query;

    Queries(String query) {
        this.query = query;
    }

}
