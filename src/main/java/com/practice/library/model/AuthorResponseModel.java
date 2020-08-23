package com.practice.library.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorResponseModel {
    private int id;
    private String name;
    private LocalDate birthdate;
}
