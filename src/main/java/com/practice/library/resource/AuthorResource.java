package com.practice.library.resource;

import com.practice.library.entity.AuthorEntity;
import com.practice.library.entity.builder.AuthorEntityBuilder;
import com.practice.library.model.AuthorRequestModel;
import com.practice.library.model.AuthorResponseModel;
import com.practice.library.model.builder.AuthorResponseModelBuilder;
import com.practice.library.repository.impl.MySQLAuthorRepositoryImpl;
import com.practice.library.service.AuthorService;
import com.practice.library.service.impl.AuthorServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/author")
public class AuthorResource {
    private final AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());
    //    @Inject
    private final AuthorResponseModelBuilder authorResponseModelBuilder = new AuthorResponseModelBuilder();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAuthor(AuthorRequestModel authorModel) {
        int id = authorService.add(AuthorEntityBuilder.create(authorModel));
        return id != -1 ? Response.status(Response.Status.CREATED).build() : Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuthorResponseModel> getAuthors() {
        return authorResponseModelBuilder.create(authorService.findAll());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{searchParameter}")
    public AuthorResponseModel getAuthor(@PathParam("searchParameter") String searchParameter) {
        AuthorEntity authorEntity = searchParameter.matches("\\d+") ?
                authorService.find(Integer.parseInt(searchParameter)) : authorService.find(searchParameter);
        return authorResponseModelBuilder.create(authorEntity);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(AuthorRequestModel authorModel) {
        boolean flag = authorService.save(AuthorEntityBuilder.create(authorModel));
        return flag ? Response.ok().build() : Response.serverError().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        boolean flag = authorService.remove(id);
        return flag ? Response.ok().build() : Response.serverError().build();
    }
}
