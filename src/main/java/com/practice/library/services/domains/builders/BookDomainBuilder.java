package com.practice.library.services.domains.builders;

import com.practice.library.api.models.BookRequestModel;
import com.practice.library.repositories.entities.BookEntity;
import com.practice.library.services.domains.AuthorDomain;
import com.practice.library.services.domains.BookDomain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookDomainBuilder {
    private final AuthorDomainBuilder authorDomainBuilder = new AuthorDomainBuilder();

    public BookDomain create(BookRequestModel bookModel) {
        return BookDomain.builder()
                .id(bookModel.getId())
                .name(bookModel.getName())
                .publishDate(LocalDate.parse(bookModel.getPublishDate()))
                .authorDomain(AuthorDomain.builder().id(bookModel.getAuthorId()).build())
                .build();
    }

    public BookDomain create(BookEntity bookEntity) {
        return BookDomain.builder()
                .id(bookEntity.getId())
                .name(bookEntity.getName())
                .publishDate(bookEntity.getPublishDate())
                .authorDomain(authorDomainBuilder.create(bookEntity.getAuthor()))
                .build();
    }

    public List<BookDomain> create(List<BookEntity> bookEntityList) {
        return bookEntityList.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
