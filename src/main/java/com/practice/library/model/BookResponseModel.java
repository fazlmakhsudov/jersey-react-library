package com.practice.library.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookResponseModel {
    private int id;
    private String name;
    private LocalDate publishDate;
    private int authorId;
}
