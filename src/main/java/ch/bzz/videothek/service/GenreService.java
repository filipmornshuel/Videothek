package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;
import ch.bzz.videothek.model.Film;
import ch.bzz.videothek.model.Genre;
import ch.bzz.videothek.model.Producer;
import ch.bzz.videothek.util.AESEncrypt;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * GenreService class
 */
@Path("genre")
public class GenreService {

    /**
     * a list of all genres
     * @return genres as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGenres(
            @CookieParam("role") String userRole
    ) {
        int httpStatus = 200;
        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole==null||userRole.equals("guest")){
            httpStatus = 403;
        }
        List<Genre> genreList = DataHandler.readAllGenres();
        return Response
                .status(httpStatus)
                .entity(genreList)
                .build();
    }

    /**
     * reads one genre by its uuid
     * @param genreUUID
     * @return response as JSON
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readGenre(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("genreUUID") String genreUUID,
            @CookieParam("role") String userRole
    ){
        Genre genre = null;
        int httpStatus = 200;
        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole==null||userRole.equals("guest")){
            httpStatus = 403;
        }
        else {
            try {
                genre = DataHandler.readGenresByUUID(genreUUID);
                if (genre == null){
                    httpStatus = 404;
                }else {
                    httpStatus = 200;
                }
            }catch (IllegalArgumentException argEx){
                httpStatus = 400;
            }
        }

        return Response
                .status(httpStatus)
                .entity(genre)
                .build();
    }

    /**
     * deletes a genre identified by its uuid
     * @param genreUUID the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteGenre(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("genreUUID") String genreUUID,
            @CookieParam("role") String userRole
    ){
        int httpStatus = 200;
        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole != null && userRole.equals("admin")){
            if (!DataHandler.deleteGenre(genreUUID)){
                httpStatus = 410;
            }
        }else {
            httpStatus = 403;
        }
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates a new genre
     * @param genre the genre
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateGenre(
            @Valid @BeanParam Genre genre,
            @CookieParam("role") String userRole
    ){
        int httpStatus = 200;

        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }

        if (userRole != null && userRole.equals("admin")){
            Genre oldGenre = DataHandler.readGenresByUUID(genre.getGenreUUID());
            if (oldGenre!=null){
                oldGenre.setGenre(genre.getGenre());
                DataHandler.updateGenre();
            }else {
                httpStatus =410;
            }
        }else{
            httpStatus = 403;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * inserts a new genre
     * @param genre the genre
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createGenre(
           @Valid @BeanParam Genre genre,
           @CookieParam("role") String userRole
    ){
        int httpStatus = 200;

        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }

        if (userRole != null && userRole.equals("admin")){
            genre.setGenreUUID(UUID.randomUUID().toString());
            DataHandler.insertGenre(genre);
        }else {
            httpStatus = 403;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

}
