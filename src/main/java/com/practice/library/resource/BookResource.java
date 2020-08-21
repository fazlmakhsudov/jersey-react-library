package com.practice.library.resource;

import com.practice.library.entity.Book;
import com.practice.library.model.BookModel;
import com.practice.library.repository.impl.MySQLBookRepositoryImpl;
import com.practice.library.service.BookService;
import com.practice.library.service.impl.BookServiceImpl;
import com.practice.library.util.EntityBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/book")
public class BookResource {
    private final BookService bookService = new BookServiceImpl(new MySQLBookRepositoryImpl());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(BookModel bookModel) {
        int id = bookService.add(EntityBuilder.buildBook(bookModel));
        return id != -1 ? Response.status(Response.Status.CREATED).build() : Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        books.addAll(bookService.findAll());
        return books;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{searchParameter}")
    public Book getBook(@PathParam("searchParameter") String searchParameter) {
        Book book = searchParameter.matches("\\d+") ?
                bookService.find(Integer.parseInt(searchParameter)) : bookService.find(searchParameter);
        if (book == null) {
            book = bookService.findByAuthor(searchParameter);
        }
        return book;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(BookModel bookModel) {
        boolean flag = bookService.save(EntityBuilder.buildBook(bookModel));
        return flag ? Response.ok().build() : Response.serverError().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") int id) {
        boolean flag = bookService.remove(id);
        return flag ? Response.ok().build() : Response.serverError().build();
    }

}
