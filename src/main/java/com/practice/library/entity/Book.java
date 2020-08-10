package com.practice.library.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    private int id;
    private String name;
    private LocalDate publishDate;
    private Author author;
}
