package com.practice.library.repositories.entities.builders;

import com.practice.library.repositories.entities.UserEntity;
import com.practice.library.services.domains.UserDomain;

public class UserEntityBuilder {
    public UserEntity create(UserDomain userDomain) {
        return UserEntity.builder()
                .id(userDomain.getId())
                .name(userDomain.getName())
                .password(userDomain.getPassword())
                .build();
    }

    public UserEntity create(String name, String password) {
        return UserEntity.builder()
                .name(name)
                .password(password)
                .build();
    }
}
