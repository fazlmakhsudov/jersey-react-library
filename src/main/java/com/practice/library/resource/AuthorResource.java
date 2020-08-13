package com.practice.library.resource;

import com.practice.library.dto.AuthorDto;
import com.practice.library.entity.Author;
import com.practice.library.repository.impl.MySQLAuthorRepositoryImpl;
import com.practice.library.service.AuthorService;
import com.practice.library.service.impl.AuthorServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Path("/author")
public class AuthorResource {
    private final Logger LOGGER = Logger.getLogger("AuthorResource");
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAuthor(AuthorDto authorDto) {
        try {
            authorService.add(authorDto.toAuthor());
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return Response.status(201).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<>();
        try {
            authors.addAll(authorService.findAll());
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return authors;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{searchParameter}")
    public Author getAuthor(@PathParam("searchParameter") String searchParameter) {
        Author author = new Author();
        try {
            author = searchParameter.matches("\\d+") ?
                    authorService.find(Integer.parseInt(searchParameter)) : authorService.find(searchParameter);
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return author;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(AuthorDto authorDto) {
        try {
            authorService.save(authorDto.toAuthor());
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        try {
            authorService.remove(id);
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return Response.ok().build();
    }

}
