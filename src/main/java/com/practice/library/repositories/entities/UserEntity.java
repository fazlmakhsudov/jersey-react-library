package com.practice.library.repositories.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserEntity {
    private int id;
    private String name;
    private String password;
}
