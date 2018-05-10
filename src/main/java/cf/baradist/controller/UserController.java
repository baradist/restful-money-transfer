package cf.baradist.controller;

import cf.baradist.model.User;
import cf.baradist.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    private UserService userService = UserService.getInstance();

    @GET
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        GenericEntity<List<User>> genericEntity = new GenericEntity<List<User>>(users) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") long id) {
        Optional<User> user = userService.getUserById(id);
        if (!user.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user.get()).build();
    }

    @POST
    public Response add(User user) {
        return Response.ok(userService.addUser(user).get()).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") long userId, User user) {
        userService.updateUser(userId, user);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long userId) {
        userService.deleteUser(userId);
        return Response.ok().build();
    }
}
