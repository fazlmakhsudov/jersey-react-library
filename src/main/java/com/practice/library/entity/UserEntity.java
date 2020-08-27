package com.practice.library.entity;

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
