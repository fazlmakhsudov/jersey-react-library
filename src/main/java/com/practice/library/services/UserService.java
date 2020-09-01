package com.practice.library.services;

import com.practice.library.services.domains.UserDomain;

public interface UserService extends BaseService<UserDomain> {
    UserDomain find(String name);
}

