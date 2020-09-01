package com.practice.library.repositories;

import com.practice.library.repositories.entities.BookEntity;

public interface BookRepository extends BaseRepository<BookEntity> {
    BookEntity readByAuthor(String name);

    BookEntity read(String name);
}
