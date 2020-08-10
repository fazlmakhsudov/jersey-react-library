package com.practice.library.dto;

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
public class AuthorDto {
    String id;
    String name;
    String birthdate;

    public Author toAuthor() {
        Author author = new Author();
        author.setId(Integer.parseInt(id));
        author.setName(name);
        LocalDate localDate = LocalDate.parse(birthdate);
        System.out.println("dto date:  " + localDate);
        author.setBirthdate(localDate);
        return author;
    }
}
