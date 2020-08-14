package com.practice.library.model;

import com.practice.library.entity.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthorModel {
    int id;
    String name;
    String birthdate;

    public static class AuthorBuilder {
        public static Author buildAuthor(AuthorModel authorModel) {
            Author author = new Author();
            author.setId(authorModel.id);
            author.setName(authorModel.name);
            LocalDate localDate = LocalDate.parse(authorModel.birthdate);
            author.setBirthdate(localDate);
            return author;
        }
    }
}
