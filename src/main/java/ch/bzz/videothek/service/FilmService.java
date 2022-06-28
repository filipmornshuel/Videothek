package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;
import ch.bzz.videothek.model.Film;
import ch.bzz.videothek.util.AESEncrypt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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
 * FilmeService class
 */
@Path("film")
public class FilmService {

    /**
     * reads a list of all films
     * @return films as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listFilms(
            @CookieParam("role") String userRole
    ) {
        int httpStatus = 200;
        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole==null||userRole.equals("guest")){
            httpStatus = 403;
        }


        List<Film> filmList = DataHandler.readAllFilms();
        return Response
                .status(httpStatus)
                .entity(filmList)
                .build();
    }

    /**
     * reads one film by its uuid
     * @param filmUUID
     * @return response as JSON
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readFilm(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("filmUUID") String filmUUID,
            @CookieParam("role") String userRole
    ){

        Film film = null;
        int httpStatus;

        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole==null||userRole.equals("guest")){
            httpStatus = 403;
        }else {
            try {
                film = DataHandler.readFilmByUUID(filmUUID);
                if (film == null){
                    httpStatus = 404;
                } else {
                    httpStatus = 200;
                }
            } catch (IllegalArgumentException argEx){
                httpStatus = 400;
            }
        }
        return Response
                .status(httpStatus)
                .entity(film)
                .build();

    }

    /**
     * deletes a film identified by its uuid
     * @param filmUUID the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteFilm(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("filmUUID") String filmUUID,
            @CookieParam("role") String userRole
    ){
        int httpStatus = 200;

        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }

        if (userRole != null && userRole.equals("admin")){
            if (!DataHandler.deleteFilm(filmUUID)){
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
     * updates a new film
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateFilm(
            @Valid @BeanParam Film film,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("filmUUID") String filmUUID,
            @Pattern(regexp="\\d{4}-(0[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")
            @FormParam("publishDate") String publishDate,
            @CookieParam("role") String userRole

    ){
        int httpStatus = 200;

        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }

        if (userRole != null && userRole.equals("admin")){

            Film oldFilm = DataHandler.readFilmByUUID(filmUUID);

            if (oldFilm!=null){
                oldFilm.setTitle(film.getTitle());
                oldFilm.setProducer(film.getProducer());
                oldFilm.setGenre(film.getGenre());
                oldFilm.setPublishDateWithString(publishDate);
                oldFilm.setPrice(film.getPrice());
                oldFilm.setLenth(film.getLenth());
                oldFilm.setEan(film.getEan());

                DataHandler.updateFilm();

            }else {
                httpStatus =410;
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
     * inserts a new film
     * @param producerUUID the uuid of the producer
     * @param genreUUID the uuid of the genre
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createFilm(
            @Valid @BeanParam Film film,
            @FormParam("producerUUID") String producerUUID,
            @FormParam("genreUUID") String genreUUID,
            @Pattern(regexp="\\d{4}-(0[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")
            @FormParam("publishDate") String publishDate,
            @CookieParam("role") String userRole
    ){
        int httpStatus = 200;

        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole != null && userRole.equals("admin")){
            film.setFilmUUID(UUID.randomUUID().toString());
            film.setProducerUUID(producerUUID);
            film.setGenreUUID(genreUUID);
            film.setPublishDateWithString(publishDate);
            DataHandler.insertFilm(film);
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
