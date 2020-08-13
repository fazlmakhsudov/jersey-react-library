package com.practice.library.resource;

import com.practice.library.entity.User;
import com.practice.library.repository.impl.MySQLUserRepositoryImpl;
import com.practice.library.service.UserService;
import com.practice.library.service.impl.UserServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

@Path("/user")
public class UserResource {
    private final Logger LOGGER = Logger.getLogger("AuthorityResource");
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    UserService userService = new UserServiceImpl(new MySQLUserRepositoryImpl());

    @POST
    @Path("/signin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAuthors(User user) {
        User foundUser = null;
        try {
            foundUser = userService.find(user.getName());
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
        }
        Response response = null;
        if (Objects.isNull(foundUser)) {
            response = Response.noContent().build();
        } else if (!foundUser.getPassword().equals(user.getPassword())) {
            response = Response.status(403).build();
        } else {
            response = Response.ok(foundUser).build();
        }
        return response;
    }
}
