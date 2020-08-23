package com.practice.library.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    private int id;
    private String name;
    private String password;
}
