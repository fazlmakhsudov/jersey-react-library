package com.practice.library.services.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserDomain {
    private int id;
    private String name;
    private String password;
}
