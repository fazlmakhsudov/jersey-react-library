package com.practice.library.resource;

import com.practice.library.dto.BookDto;
import com.practice.library.entity.Book;
import com.practice.library.repository.impl.MySQLBookRepositoryImpl;
import com.practice.library.service.BookService;
import com.practice.library.service.impl.BookServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/book")
public class BookResource {
    private final Logger LOGGER = Logger.getLogger("BookResource");
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    BookService bookService = new BookServiceImpl(new MySQLBookRepositoryImpl());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(BookDto bookDto) {
        try {
            bookService.add(bookDto.toBook());
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return Response.status(201).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        try {
            books.addAll(bookService.findAll());
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return books;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{searchParameter}")
    public Book getBook(@PathParam("searchParameter") String searchParameter) {
        Book book = new Book();
        try {
            book = searchParameter.matches("\\d+") ?
                    bookService.find(Integer.parseInt(searchParameter)) : bookService.find(searchParameter);
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return book;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(BookDto bookDto) {
        try {
            bookService.save(bookDto.toBook());
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
            ex.printStackTrace();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") int id) {
        try {
            bookService.remove(id);
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return Response.ok().build();
    }

}
