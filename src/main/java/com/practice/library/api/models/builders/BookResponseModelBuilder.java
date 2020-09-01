package com.practice.library.api.models.builders;


import com.practice.library.api.models.BookResponseModel;
import com.practice.library.services.domains.BookDomain;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class BookResponseModelBuilder {
    public BookResponseModel create(BookDomain bookDomain) {
        return BookResponseModel.builder()
                .id(bookDomain.getId())
                .name(bookDomain.getName())
                .publishDate(bookDomain.getPublishDate())
                .authorId(bookDomain.getAuthorDomain().getId())
                .build();
    }

    public List<BookResponseModel> create(List<BookDomain> bookDomains) {
        return bookDomains.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
