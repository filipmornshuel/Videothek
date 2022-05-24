package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;
import ch.bzz.videothek.model.Film;
import ch.bzz.videothek.model.Genre;
import ch.bzz.videothek.model.Producer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for reading, adding, changing and deleting books
 */
@Path("genre")
public class GenreService {
    /**
     * reads a list of all books
     * @return books as JSON
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGenres() {
        List<Genre> genreList = DataHandler.getInstance().readAllGenres();
        return Response
                .status(200)
                .entity(genreList)
                .build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readGenre(
            @QueryParam("uuid") String genreUUID
    ){
        if (genreUUID.isEmpty()){
            new IllegalArgumentException("illegal uuid");
            return Response.status(400).entity(null).build();
        }else {
            Genre genre = DataHandler.getInstance().readGenresByUUID(genreUUID);
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

}
