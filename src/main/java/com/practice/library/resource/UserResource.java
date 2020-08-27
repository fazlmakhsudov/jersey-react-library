package com.practice.library.resource;

import com.practice.library.entity.UserEntity;
import com.practice.library.model.UserRequestModel;
import com.practice.library.repository.impl.MySQLUserRepositoryImpl;
import com.practice.library.service.UserService;
import com.practice.library.service.impl.UserServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/user")
public class UserResource {
    private final UserService userService = new UserServiceImpl(new MySQLUserRepositoryImpl());

    @POST
    @Path("/signin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUser(UserRequestModel userModel) {
        UserEntity foundUser = userService.find(userModel.getName());
        Response response;
        if (Objects.isNull(foundUser)) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else if (!foundUser.getPassword().equals(userModel.getPassword())) {
            response = Response.status(Response.Status.FORBIDDEN).build();
        } else {
            response = Response.ok(foundUser).build();
        }
        return response;
    }
}
