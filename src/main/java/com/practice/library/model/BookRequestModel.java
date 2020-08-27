package com.practice.library.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookRequestModel {
    private int id;
    private String name;
    private String publishDate;
    private int authorId;
}
