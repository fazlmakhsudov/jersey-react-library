package com.practice.library.model.builder;


import com.practice.library.entity.BookEntity;
import com.practice.library.model.BookResponseModel;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class BookResponseModelBuilder {
    public BookResponseModel create(BookEntity bookEntity) {
        BookResponseModel bookResponseModel = new BookResponseModel();
        bookResponseModel.setId(bookEntity.getId());
        bookResponseModel.setName(bookEntity.getName());
        bookResponseModel.setPublishDate(bookEntity.getPublishDate());
        bookResponseModel.setAuthorId(bookEntity.getAuthor().getId());
        return bookResponseModel;
    }

    public List<BookResponseModel> create(List<BookEntity> bookEntities) {
        return bookEntities.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
