package com.practice.library.repositories.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class BookEntity {
    private int id;
    private String name;
    private LocalDate publishDate;
    private AuthorEntity author;
}
