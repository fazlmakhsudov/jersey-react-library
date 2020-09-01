package com.practice.library.repositories.entities.builders;

import com.practice.library.repositories.entities.AuthorEntity;
import com.practice.library.repositories.entities.BookEntity;
import com.practice.library.services.domains.BookDomain;

public class BookEntityBuilder {
    public BookEntity create(BookDomain bookDomain) {
        return BookEntity.builder()
                .id(bookDomain.getId())
                .name(bookDomain.getName())
                .publishDate(bookDomain.getPublishDate())
                .authorEntity(AuthorEntity.builder().id(bookDomain.getAuthorDomain().getId()).build())
                .build();
    }
}
