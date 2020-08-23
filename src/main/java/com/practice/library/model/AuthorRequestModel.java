package com.practice.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthorRequestModel {
    private int id;
    private String name;
    private String birthdate;
}
