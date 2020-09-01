package com.practice.library.api.resources.v1;

import com.practice.library.api.models.BookRequestModel;
import com.practice.library.api.models.BookResponseModel;
import com.practice.library.api.models.builders.BookResponseModelBuilder;
import com.practice.library.repositories.MySQLBookRepositoryImpl;
import com.practice.library.services.BookService;
import com.practice.library.services.BookServiceImpl;
import com.practice.library.services.domains.BookDomain;
import com.practice.library.services.domains.builders.BookDomainBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Path("/book")
public class BookResource {
    private final BookService bookService = new BookServiceImpl(new MySQLBookRepositoryImpl());
    //    @Inject
    private final BookResponseModelBuilder bookResponseModelBuilder = new BookResponseModelBuilder();
    private final BookDomainBuilder bookDomainBuilder = new BookDomainBuilder();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(BookRequestModel bookModel) {
        int id = bookService.add(bookDomainBuilder.create(bookModel));
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
    public Response getBook(@PathParam("searchParameter") String searchParameter) {
        BookDomain book = searchParameter.matches("\\d+") ?
                bookService.find(Integer.parseInt(searchParameter)) : bookService.find(searchParameter);
        if (book == null) {
            book = bookService.findByAuthor(searchParameter);
        }
        return Objects.isNull(book) ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.ok(bookResponseModelBuilder.create(book)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(BookRequestModel bookModel) {
        boolean flag = bookService.save(bookDomainBuilder.create(bookModel));
        return flag ? Response.ok().build() : Response.serverError().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") int id) {
        boolean flag = bookService.remove(id);
        return flag ? Response.ok().build() : Response.serverError().build();
    }

}
