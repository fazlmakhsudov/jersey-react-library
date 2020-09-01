package com.practice.library.services;

import com.practice.library.services.domains.AuthorDomain;

public interface AuthorService extends BaseService<AuthorDomain> {
    AuthorDomain find(String name);
}

