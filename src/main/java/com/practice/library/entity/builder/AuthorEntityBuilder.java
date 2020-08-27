package com.practice.library.entity.builder;

import com.practice.library.entity.AuthorEntity;
import com.practice.library.model.AuthorRequestModel;

import java.time.LocalDate;

public class AuthorEntityBuilder {
    public AuthorEntity create(AuthorRequestModel authorModel) {
        AuthorEntity author = AuthorEntity.builder()
                .id(authorModel.getId())
                .name(authorModel.getName())
                .birthdate(LocalDate.parse(authorModel.getBirthdate()))
                .build();
        return author;
    }
}
