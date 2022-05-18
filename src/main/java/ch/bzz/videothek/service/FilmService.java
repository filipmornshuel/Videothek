package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;
import ch.bzz.videothek.model.Film;

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
@Path("film")
public class FilmService {
    /**
     * reads a list of all books
     * @return books as JSON
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listFilms() {
        List<Film> filmList = DataHandler.getInstance().readAllFilms();
        return Response
                .status(200)
                .entity(filmList)
                .build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(
            @QueryParam("uuid") String filmUUID
    ){
        if (filmUUID.isEmpty()){
            new IllegalArgumentException("illegal uuid");
            return Response.status(400).entity(null).build();
        }else {
            Film film = DataHandler.getInstance().readFilmByUUID(filmUUID);
            if (film!=null) {
                return Response
                        .status(200)
                        .entity(film)
                        .build();
            }else {
                return Response.status(404).entity(film).build();
            }
        }
    }
}
