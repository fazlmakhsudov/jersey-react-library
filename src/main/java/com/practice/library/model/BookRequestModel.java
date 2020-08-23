package com.practice.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookRequestModel {
    private int id;
    private String name;
    private String publishDate;
    private int authorId;
}
