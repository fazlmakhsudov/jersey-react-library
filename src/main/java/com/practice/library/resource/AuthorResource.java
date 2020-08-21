package com.practice.library.resource;

import com.practice.library.entity.Author;
import com.practice.library.model.AuthorModel;
import com.practice.library.repository.impl.MySQLAuthorRepositoryImpl;
import com.practice.library.service.AuthorService;
import com.practice.library.service.impl.AuthorServiceImpl;
import com.practice.library.util.EntityBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("/author")
public class AuthorResource {
    private final AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAuthor(AuthorModel authorModel) {
        int id = authorService.add(EntityBuilder.buildAuthor(authorModel));
        return id != -1 ? Response.status(Response.Status.CREATED).build() : Response.serverError().build();
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
        return searchParameter.matches("\\d+") ?
                authorService.find(Integer.parseInt(searchParameter)) : authorService.find(searchParameter);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(AuthorModel authorModel) {
        boolean flag = authorService.save(EntityBuilder.buildAuthor(authorModel));
        return flag ? Response.ok().build() : Response.serverError().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        boolean flag = authorService.remove(id);
        return flag ? Response.ok().build() : Response.serverError().build();
    }
}
