package com.practice.library.services.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class BookDomain {
    private int id;
    private String name;
    private LocalDate publishDate;
    private AuthorDomain authorDomain;
}
