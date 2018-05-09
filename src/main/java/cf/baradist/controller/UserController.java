package cf.baradist.controller;

import cf.baradist.model.User;
import cf.baradist.services.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    private UserService userService = UserService.getInstance();

    @GET
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        GenericEntity<List<User>> genericEntity = new GenericEntity<List<User>>(users) {};
        return Response.ok(genericEntity).build();
    }
}
