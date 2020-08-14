package com.practice.library.resource;

import com.practice.library.entity.Author;
import com.practice.library.model.AuthorModel;
import com.practice.library.repository.impl.MySQLAuthorRepositoryImpl;
import com.practice.library.service.AuthorService;
import com.practice.library.service.impl.AuthorServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;


@Path("/author")
public class AuthorResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAuthor(AuthorModel authorModel) {
        int id = authorService.add(AuthorModel.AuthorBuilder.buildAuthor(authorModel));
        return id != -1 ? Response.status(201).build() : Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.addAll(authorService.findAll());
        return authors;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{searchParameter}")
    public Author getAuthor(@PathParam("searchParameter") String searchParameter) {
        Author author = searchParameter.matches("\\d+") ?
                authorService.find(Integer.parseInt(searchParameter)) : authorService.find(searchParameter);
        return author;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(AuthorModel authorModel) {
        boolean flag = authorService.save(AuthorModel.AuthorBuilder.buildAuthor(authorModel));
        return flag ? Response.ok().build() : Response.serverError().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        boolean flag = authorService.remove(id);
        return flag ? Response.ok().build() : Response.serverError().build();
    }
}
