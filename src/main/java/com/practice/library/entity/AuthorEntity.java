package com.practice.library.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorEntity {
    private int id;
    private String name;
    private LocalDate birthdate;
    private List<BookEntity> books;
}