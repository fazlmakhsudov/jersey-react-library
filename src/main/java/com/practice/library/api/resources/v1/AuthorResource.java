package com.practice.library.api.resources.v1;

import com.practice.library.api.models.AuthorRequestModel;
import com.practice.library.api.models.AuthorResponseModel;
import com.practice.library.api.models.builders.AuthorResponseModelBuilder;
import com.practice.library.repositories.MySQLAuthorRepositoryImpl;
import com.practice.library.services.AuthorService;
import com.practice.library.services.AuthorServiceImpl;
import com.practice.library.services.domains.AuthorDomain;
import com.practice.library.services.domains.builders.AuthorDomainBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;


@Path("/author")
public class AuthorResource {
    private final AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());
    //    @Inject
    private final AuthorResponseModelBuilder authorResponseModelBuilder = new AuthorResponseModelBuilder();
    private final AuthorDomainBuilder authorDomainBuilder = new AuthorDomainBuilder();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAuthor(AuthorRequestModel authorModel) {
        int id = authorService.add(authorDomainBuilder.create(authorModel));
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
    public Response getAuthor(@PathParam("searchParameter") String searchParameter) {
        AuthorDomain authorDomain = searchParameter.matches("\\d+") ?
                authorService.find(Integer.parseInt(searchParameter)) : authorService.find(searchParameter);
        return Objects.isNull(authorDomain) ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.ok(authorResponseModelBuilder.create(authorDomain)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(AuthorRequestModel authorModel) {
        boolean flag = authorService.save(authorDomainBuilder.create(authorModel));
        return flag ? Response.ok().build() : Response.serverError().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        boolean flag = authorService.remove(id);
        return flag ? Response.ok().build() : Response.serverError().build();
    }
}
