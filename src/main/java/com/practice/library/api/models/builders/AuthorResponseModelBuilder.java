package com.practice.library.api.models.builders;


import com.practice.library.api.models.AuthorResponseModel;
import com.practice.library.services.domains.AuthorDomain;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AuthorResponseModelBuilder {
    public AuthorResponseModel create(AuthorDomain authorDomain) {
        return AuthorResponseModel.builder()
                .id(authorDomain.getId())
                .name(authorDomain.getName())
                .birthdate(authorDomain.getBirthdate())
                .build();
    }

    public List<AuthorResponseModel> create(List<AuthorDomain> authorDomains) {
        return authorDomains.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
