package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;
import ch.bzz.videothek.model.Film;
import ch.bzz.videothek.model.Genre;
import ch.bzz.videothek.model.Producer;

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
    public Response listGenres() {
        List<Genre> genreList = DataHandler.readAllGenres();
        return Response
                .status(200)
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
            @Pattern(regexp = "[8-9a-fA-F]{8}-([8-9a-fA-F]{4}-){3}[8-9a-fA-F]{12}")
            @QueryParam("uuid") String genreUUID
    ){
        if (genreUUID.isEmpty()){
            new IllegalArgumentException("illegal uuid");
            return Response.status(400).entity(null).build();
        }else {
            Genre genre = DataHandler.readGenresByUUID(genreUUID);
            if (genre!=null) {
                return Response
                        .status(200)
                        .entity(genre)
                        .build();
            }else {
                return Response.status(404).entity(genre).build();
            }
        }
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
            @Pattern(regexp = "[8-9a-fA-F]{8}-([8-9a-fA-F]{4}-){3}[8-9a-fA-F]{12}")
            @QueryParam("uuid") String genreUUID
    ){
        int httpStatus = 200;

        if (!DataHandler.deleteGenre(genreUUID)){
            httpStatus = 410;
        }
        /*
        if (!genreUUID.isEmpty()){

            if (DataHandler.readGenresByUUID(genreUUID)!=null){
                DataHandler.deleteGenre(genreUUID);
                httpStatus = 200;
            }else {
                httpStatus = 404;
            }

        }else {
            httpStatus = 400;
        }

         */
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
            @Valid @BeanParam Genre genre
    ){
        int httpStatus = 200;
        Genre oldGenre = DataHandler.readGenresByUUID(genre.getGenreUUID());
        if (oldGenre!=null){
            setAttributes(
                    oldGenre,
                    genre.getGenre()
            );

            DataHandler.updateGenre();
        }else {
            httpStatus =410;
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
           @Valid @BeanParam Genre genre
    ){
        int httpStatus = 200;
        genre.setGenreUUID(UUID.randomUUID().toString());
        /*
        Genre genreObj = new Genre();
        genreObj.setGenre(genre);
        DataHandler.updateGenre();
         */
        DataHandler.insertGenre(genre);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * sets the attributes for the genre-object
     * @param genre the genre
     * @param genreName the name of the genre
     */
    private void setAttributes(
            Genre genre,
            String genreName
    ) {
       genre.setGenre(genreName);
    }
}
