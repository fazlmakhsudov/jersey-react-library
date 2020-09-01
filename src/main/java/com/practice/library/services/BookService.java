package com.practice.library.services;


import com.practice.library.services.domains.BookDomain;

public interface BookService extends BaseService<BookDomain> {
    BookDomain findByAuthor(String name);

    BookDomain find(String name);
}
