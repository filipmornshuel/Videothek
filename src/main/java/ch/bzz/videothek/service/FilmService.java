package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;
import ch.bzz.videothek.model.Film;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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
    public Response listFilms() {
        List<Film> filmList = DataHandler.readAllFilms();
        return Response
                .status(200)
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
            @Pattern(regexp = "[8-9a-fA-F]{8}-([8-9a-fA-F]{4}-){3}[8-9a-fA-F]{12}")
            @QueryParam("uuid") String filmUUID
    ){
        if (filmUUID.isEmpty()){
            new IllegalArgumentException("illegal uuid");
            return Response.status(400).entity(null).build();
        }else {
            Film film = DataHandler.readFilmByUUID(filmUUID);
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
            @Pattern(regexp = "[8-9a-fA-F]{8}-([8-9a-fA-F]{4}-){3}[8-9a-fA-F]{12}")
            @QueryParam("uuid") String filmUUID
    ){
        int httpStatus = 200;

        if (!filmUUID.isEmpty()){

            if (DataHandler.readFilmByUUID(filmUUID)!=null){
                DataHandler.deleteFilm(filmUUID);
                httpStatus = 200;
            }else {
                httpStatus = 404;
            }

        }else {
            httpStatus = 400;
        }
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates a new film
     * @param filmUUID the key
     * @param title the title
     * @param producerUUID the uuid of the producer
     * @param genreUUID the uuid of the genre
     * @param price the price
     * @param publishDate the publishDate
     * @param lenth the lenth
     * @param ean the ean
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateFilm(
            @FormParam("filmUUID") String filmUUID,
            @FormParam("title") String title,
            @FormParam("producerUUID") String producerUUID,
            @FormParam("genreUUID") String genreUUID,
            @FormParam("price") BigDecimal price,
            //Hier mit Beans und Validierung nachfragen etvl. bei Kevin oder Jacobi
            @FormParam("publishDate") LocalDate publishDate,
            @FormParam("lenth") Integer lenth,
            @FormParam("ean") String ean
    ){
        int httpStatus = 200;
        if (!filmUUID.isEmpty()){
            Film film = DataHandler.readFilmByUUID(filmUUID);
            if (film!=null){
                film.setTitle(title);
                film.setProducerUUID(producerUUID);
                film.setGenreUUID(genreUUID);
                film.setPrice(price);
                film.setPublishDate(publishDate);
                film.setLenth(lenth);
                film.setEan(ean);
                DataHandler.updateFilm();
                httpStatus =200;
            }else {
                httpStatus =404;
            }
        }else {
            httpStatus = 400;
        }
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * inserts a new film
     * @param filmUUID
     * @param title the title
     * @param producerUUID the uuid of the producer
     * @param genreUUID the uuid of the genre
     * @param price the price
     * @param publishDate the publishDate
     * @param lenth the lenth
     * @param ean the ean
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createFilm(
            @FormParam("filmUUID") String filmUUID,
            @FormParam("title") String title,
            @FormParam("producerUUID") String producerUUID,
            @FormParam("genreUUID") String genreUUID,
            @FormParam("price") BigDecimal price,
            @FormParam("publishDate") String publishDate,
            @FormParam("lenth") Integer lenth,
            @FormParam("ean") String ean
    ){
        int httpStatus = 200;
        Film film = new Film();
        film.setTitle(title);
        film.setProducerUUID(producerUUID);
        film.setGenreUUID(genreUUID);
        film.setPrice(price);
        film.setEan(ean);
        DataHandler.updateFilm();

        DataHandler.insertFilm(film);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

}
