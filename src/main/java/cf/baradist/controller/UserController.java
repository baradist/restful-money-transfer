package cf.baradist.controller;

import cf.baradist.exception.ApiException;
import cf.baradist.model.User;
import cf.baradist.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/user")
@Api(value = "user", description = "Operations about user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    private UserService userService = UserService.getInstance();

    @GET
    @ApiOperation(value = "Get all users",
            notes = "Returns the whole list of users")
    public Response getAllUsers() throws SQLException {
        List<User> users = userService.getAllUsers();
        GenericEntity<List<User>> genericEntity = new GenericEntity<List<User>>(users) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("{id}")
    @ApiOperation(value = "Get user by ID",
            notes = "Gets a user by a given ID")
    public Response get(@PathParam("id") Long id) throws SQLException, ApiException {
        return Response.ok(userService.getUserById(id).get()).build();
    }

    @POST
    @ApiOperation(value = "Create user",
            notes = "Creates a new user")
    public Response add(User user) throws SQLException {
        return Response.ok(userService.addUser(user).get()).build();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update user",
            notes = "Updates fields of a user with a given ID")
    public Response update(@PathParam("id") long userId, User user) throws SQLException, ApiException {
        userService.updateUser(userId, user);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Delete user",
            notes = "Removes a user with a given ID")
    public Response delete(@PathParam("id") long userId) throws SQLException, ApiException {
        userService.deleteUser(userId);
        return Response.ok().build();
    }
}
