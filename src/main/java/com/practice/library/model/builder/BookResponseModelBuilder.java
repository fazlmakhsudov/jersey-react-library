package com.practice.library.model.builder;


import com.practice.library.entity.BookEntity;
import com.practice.library.model.BookResponseModel;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class BookResponseModelBuilder {
    public BookResponseModel create(BookEntity bookEntity) {
        return BookResponseModel.builder()
                .id(bookEntity.getId())
                .name(bookEntity.getName())
                .publishDate(bookEntity.getPublishDate())
                .authorId(bookEntity.getAuthor().getId())
                .build();
    }

    public List<BookResponseModel> create(List<BookEntity> bookEntities) {
        return bookEntities.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
