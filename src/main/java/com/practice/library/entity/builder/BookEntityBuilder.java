package com.practice.library.entity.builder;

import com.practice.library.entity.AuthorEntity;
import com.practice.library.entity.BookEntity;
import com.practice.library.model.BookRequestModel;

import java.time.LocalDate;

public class BookEntityBuilder {
    public BookEntity create(BookRequestModel bookModel) {
        return BookEntity.builder()
                .id(bookModel.getId())
                .name(bookModel.getName())
                .publishDate(LocalDate.parse(bookModel.getPublishDate()))
                .author(AuthorEntity.builder().id(bookModel.getAuthorId()).build())
                .build();
    }
}
