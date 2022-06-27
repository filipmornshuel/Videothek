package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;
import ch.bzz.videothek.data.UserData;
import ch.bzz.videothek.model.User;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.awt.*;

/**
 * services for authentication and authorization of users
 */

@Path("user")
public class UserService {

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ){
        int httpStatus;
        User user = UserData.findUser(username, password);
        if (user == null || user.getRole() == null || user.getRole().equals("guest")){
            httpStatus = 404;
        }else {
            httpStatus =200;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                user.getRole(),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }

    @DELETE
    @Path("logoff")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logoff(){

        Response response = Response
                .status(200)
                .entity("")
                .build();
        return response;
    }
}

