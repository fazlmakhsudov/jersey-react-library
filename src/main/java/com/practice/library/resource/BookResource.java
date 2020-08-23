package com.practice.library.resource;

import com.practice.library.entity.BookEntity;
import com.practice.library.entity.builder.BookEntityBuilder;
import com.practice.library.model.BookRequestModel;
import com.practice.library.model.BookResponseModel;
import com.practice.library.model.builder.BookResponseModelBuilder;
import com.practice.library.repository.impl.MySQLBookRepositoryImpl;
import com.practice.library.service.BookService;
import com.practice.library.service.impl.BookServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/book")
public class BookResource {
    private final BookService bookService = new BookServiceImpl(new MySQLBookRepositoryImpl());
    //    @Inject
    private final BookResponseModelBuilder bookResponseModelBuilder = new BookResponseModelBuilder();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(BookRequestModel bookModel) {
        int id = bookService.add(BookEntityBuilder.create(bookModel));
        return id != -1 ? Response.status(Response.Status.CREATED).build() : Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookResponseModel> getBooks() {
        return bookResponseModelBuilder.create(bookService.findAll());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{searchParameter}")
    public BookResponseModel getBook(@PathParam("searchParameter") String searchParameter) {
        BookEntity book = searchParameter.matches("\\d+") ?
                bookService.find(Integer.parseInt(searchParameter)) : bookService.find(searchParameter);
        if (book == null) {
            book = bookService.findByAuthor(searchParameter);
        }
        return bookResponseModelBuilder.create(book);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(BookRequestModel bookModel) {
        boolean flag = bookService.save(BookEntityBuilder.create(bookModel));
        return flag ? Response.ok().build() : Response.serverError().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") int id) {
        boolean flag = bookService.remove(id);
        return flag ? Response.ok().build() : Response.serverError().build();
    }

}
