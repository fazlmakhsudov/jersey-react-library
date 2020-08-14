package com.practice.library.model;

import com.practice.library.entity.Author;
import com.practice.library.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookModel {
    int id;
    String name;
    String publishDate;
    int authorId;

    public static class BookBuilder {
        public static Book buildBook(BookModel bookModel) {
            Book book = new Book();
            book.setId(bookModel.id);
            book.setName(bookModel.name);
            LocalDate localDate = LocalDate.parse(bookModel.publishDate);
            book.setPublishDate(localDate);
            int authorId = bookModel.authorId;
            Author author = new Author();
            author.setId(authorId);
            book.setAuthor(author);
            return book;
        }
    }
}
