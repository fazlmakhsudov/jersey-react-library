package com.practice.library.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class BookResponseModel {
    private int id;
    private String name;
    private LocalDate publishDate;
    private int authorId;
}
