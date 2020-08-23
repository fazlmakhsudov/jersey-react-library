package com.practice.library.repository;

import com.practice.library.entity.BookEntity;

public interface BookRepository extends BaseRepository<BookEntity> {
    BookEntity readByAuthor(String name);

    BookEntity read(String name);
}
