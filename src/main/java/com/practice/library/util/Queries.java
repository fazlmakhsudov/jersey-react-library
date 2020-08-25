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
    READ_BOOK_BY_ID("select * from book b inner join author a ON b.authorId = a.id WHERE b.id = ?;"),
    READ_BOOK_BY_NAME("select * from book b inner join author a ON b.authorId = a.id WHERE b.name LIKE ?;"),
    READ_BOOK_BY_AUTHOR_NAME("select * from book b inner join author a ON b.authorId = a.id WHERE a.name LIKE ?;"),
    READ_ALL_BOOKS("SELECT * FROM book inner join author ON book.authorId = author.id;"),
    UPDATE_BOOK("UPDATE book SET name = ?, publishDate = ?, authorId = ? WHERE id = ?;"),
    DELETE_BOOK("DELETE FROM book where id = ?;"),
    CREATE_USER("INSERT INTO user (name, password) VALUES (?, ?);"),
    READ_USER_BY_NAME("SELECT * FROM user WHERE name LIKE ?;"),
    READ_ALL_USERS("SELECT * FROM user;"),
    UPDATE_USER("UPDATE user SET name = ?, password = ? WHERE id = ?;"),
    DELETE_USER("DELETE FROM user where id = ?;"),
    ;
    private final String query;

    Queries(String query) {
        this.query = query;
    }

}
