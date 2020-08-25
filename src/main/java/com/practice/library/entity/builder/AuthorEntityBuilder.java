package com.practice.library.entity.builder;

import com.practice.library.entity.AuthorEntity;
import com.practice.library.model.AuthorRequestModel;

import java.time.LocalDate;

public class AuthorEntityBuilder {
    public AuthorEntity create(AuthorRequestModel authorModel) {
        AuthorEntity author = new AuthorEntity();
        author.setId(authorModel.getId());
        author.setName(authorModel.getName());
        LocalDate localDate = LocalDate.parse(authorModel.getBirthdate());
        author.setBirthdate(localDate);
        return author;
    }
}
