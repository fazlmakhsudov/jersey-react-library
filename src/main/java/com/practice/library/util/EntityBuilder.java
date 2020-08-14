package com.practice.library.util;

import com.practice.library.entity.Author;
import com.practice.library.entity.Book;
import com.practice.library.model.AuthorModel;
import com.practice.library.model.BookModel;

import java.time.LocalDate;

public class EntityBuilder {
    public static Book buildBook(BookModel bookModel) {
        Book book = new Book();
        book.setId(bookModel.getId());
        book.setName(bookModel.getName());
        LocalDate localDate = LocalDate.parse(bookModel.getPublishDate());
        book.setPublishDate(localDate);
        int authorId = bookModel.getAuthorId();
        Author author = new Author();
        author.setId(authorId);
        book.setAuthor(author);
        return book;
    }

    public static Author buildAuthor(AuthorModel authorModel) {
        Author author = new Author();
        author.setId(authorModel.getId());
        author.setName(authorModel.getName());
        LocalDate localDate = LocalDate.parse(authorModel.getBirthdate());
        author.setBirthdate(localDate);
        return author;
    }
}
