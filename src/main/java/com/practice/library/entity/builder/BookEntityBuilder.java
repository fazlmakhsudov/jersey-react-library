package com.practice.library.entity.builder;

import com.practice.library.entity.AuthorEntity;
import com.practice.library.entity.BookEntity;
import com.practice.library.model.BookRequestModel;

import java.time.LocalDate;

public class BookEntityBuilder {
    public BookEntity create(BookRequestModel bookModel) {
        BookEntity book = new BookEntity();
        book.setId(bookModel.getId());
        book.setName(bookModel.getName());
        LocalDate localDate = LocalDate.parse(bookModel.getPublishDate());
        book.setPublishDate(localDate);
        int authorId = bookModel.getAuthorId();
        AuthorEntity author = new AuthorEntity();
        author.setId(authorId);
        book.setAuthor(author);
        return book;
    }
}
