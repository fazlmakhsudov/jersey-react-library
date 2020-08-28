package com.practice.library.model.builder;


import com.practice.library.entity.AuthorEntity;
import com.practice.library.model.AuthorResponseModel;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AuthorResponseModelBuilder {
    public AuthorResponseModel create(AuthorEntity authorEntity) {
        return AuthorResponseModel.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getName())
                .birthdate(authorEntity.getBirthdate())
                .build();
    }

    public List<AuthorResponseModel> create(List<AuthorEntity> authorEntities) {
        return authorEntities.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
