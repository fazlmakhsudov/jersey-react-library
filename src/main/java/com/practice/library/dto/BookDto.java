package com.practice.library.dto;

import com.practice.library.entity.Author;
import com.practice.library.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookDto {
    String id;
    String name;
    String publishDate;
    String authorId;

    public Book toBook() {
        Book book = new Book();
        book.setId(Integer.parseInt(id));
        book.setName(name);
        LocalDate localDate = LocalDate.parse(publishDate);
        book.setPublishDate(localDate);
        Optional<String> optionalAuthorId = Optional.ofNullable(this.authorId);
        int authorId = optionalAuthorId.orElse("").matches("\\d+") ?
                Integer.parseInt(this.authorId) : 1;
        Author author = new Author();
        author.setId(authorId);
        book.setAuthor(author);
        return book;
    }
}
