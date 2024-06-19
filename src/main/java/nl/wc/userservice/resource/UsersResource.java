package nl.wc.userservice.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import nl.wc.userservice.model.User;
import nl.wc.userservice.service.UserService;

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

    @POST
    @Path("login")
    public User login(User u) {
        u = userService.login(u);
        return u;
    }

    @Path("{id}")
    public UserResource toUserResource(@PathParam("id") int id) {
        return userResource.with(id);
    }

}