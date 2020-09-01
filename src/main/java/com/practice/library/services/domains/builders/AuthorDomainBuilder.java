package com.practice.library.services.domains.builders;

import com.practice.library.api.models.AuthorRequestModel;
import com.practice.library.repositories.entities.AuthorEntity;
import com.practice.library.services.domains.AuthorDomain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorDomainBuilder {
    public AuthorDomain create(AuthorRequestModel authorModel) {
        return AuthorDomain.builder()
                .id(authorModel.getId())
                .name(authorModel.getName())
                .birthdate(LocalDate.parse(authorModel.getBirthdate()))
                .build();
    }

    public AuthorDomain create(AuthorEntity authorEntity) {
        return AuthorDomain.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getName())
                .birthdate(authorEntity.getBirthdate())
                .build();
    }

    public List<AuthorDomain> create(List<AuthorEntity> authorEntityList) {
        return authorEntityList.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
