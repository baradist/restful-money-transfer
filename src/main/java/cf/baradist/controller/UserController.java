package cf.baradist.controller;

import cf.baradist.model.User;
import cf.baradist.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/user")
@Api(value = "user", description = "Operations about user")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserController {
    private UserService userService = UserService.getInstance();

    @GET
    @ApiOperation(value = "Get all users",
            notes = "Returns the whole list of users")
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        GenericEntity<List<User>> genericEntity = new GenericEntity<List<User>>(users) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("{id}")
    @ApiOperation(value = "Get user by ID",
            notes = "Gets a user by a given ID")
    public Response get(@PathParam("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(u ->
                Response.ok(u).build()).orElseGet(() ->
                Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @ApiOperation(value = "Create user",
            notes = "Creates a new user")
    public Response add(User user) {
        return Response.ok(userService.addUser(user).get()).build();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update user",
            notes = "Updates fields of a user with a given ID")
    public Response update(@PathParam("id") long userId, User user) {
        userService.updateUser(userId, user);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Delete user",
            notes = "Removes a user with a given ID")
    public Response delete(@PathParam("id") long userId) {
        userService.deleteUser(userId);
        return Response.ok().build();
    }
}
