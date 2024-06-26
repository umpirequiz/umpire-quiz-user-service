package nl.wc.userservice.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import nl.wc.userservice.model.Auth;
import nl.wc.userservice.model.LoginRequest;
import nl.wc.userservice.model.User;
import nl.wc.userservice.service.UserService;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/auth")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UsersResource {
    private final UserService userService;

    private final UserResource userResource;

    @Inject
    public UsersResource(UserService userService, UserResource userResource) {
        this.userService = userService;
        this.userResource = userResource;
    }

    @POST
    public User register(User u) {
        u = userService.createUser(u);
        return u;
    }

    @GET
    public List<User> users() {
        return userService.getAllUsers();
    }

    @POST
    @Path("login")
    public Auth login(LoginRequest request) {
        return userService.login(request.getUsername(), request.getPassword());
    }

    @Path("{id}")
    public UserResource toUserResource(@PathParam("id") int id) {
        return userResource.with(id);
    }

}