package com.practice.library.repositories.entities.builders;

import com.practice.library.repositories.entities.AuthorEntity;
import com.practice.library.services.domains.AuthorDomain;

public class AuthorEntityBuilder {
    public AuthorEntity create(AuthorDomain authorDomain) {
        return AuthorEntity.builder()
                .id(authorDomain.getId())
                .name(authorDomain.getName())
                .birthdate(authorDomain.getBirthdate())
                .build();
    }
}
