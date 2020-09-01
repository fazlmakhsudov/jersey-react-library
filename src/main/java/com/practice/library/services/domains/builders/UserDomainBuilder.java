package com.practice.library.services.domains.builders;

import com.practice.library.api.models.UserRequestModel;
import com.practice.library.repositories.entities.UserEntity;
import com.practice.library.services.domains.UserDomain;

import java.util.List;
import java.util.stream.Collectors;

public class UserDomainBuilder {
    public UserDomain create(UserRequestModel userModel) {
        return UserDomain.builder()
                .name(userModel.getName())
                .password(userModel.getPassword())
                .build();
    }

    public UserDomain create(UserEntity userEntity) {
        return UserDomain.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .build();
    }

    public List<UserDomain> create(List<UserEntity> userEntityList) {
        return userEntityList.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }
}
