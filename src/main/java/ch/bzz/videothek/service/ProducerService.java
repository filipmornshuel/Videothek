package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;
import ch.bzz.videothek.model.Film;
import ch.bzz.videothek.model.Genre;
import ch.bzz.videothek.model.Producer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * ProducerService class
 */
@Path("producer")
public class ProducerService {

    /**
     * a list of all producers
     * @return producers as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listProducers() {
        List<Producer> producerList = DataHandler.readAllProducers();
        return Response
                .status(200)
                .entity(producerList)
                .build();
    }

    /**
     * reads one producer by its uuid
     * @param producerUUID
     * @return response as JSON
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readProducer(
            @QueryParam("uuid") String producerUUID
    ){
        if (producerUUID.isEmpty()){
            new IllegalArgumentException("illegal uuid");
            return Response.status(400).entity(null).build();
        }else {
            Producer producer = DataHandler.readProducersByUUID(producerUUID);
            if (producer!=null) {
                return Response
                        .status(200)
                        .entity(producer)
                        .build();
            }else {
                return Response.status(404).entity(producer).build();
            }
        }
    }

    /**
     * deletes a producer identified by its uuid
     * @param producerUUID the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteProducer(
            @QueryParam("uuid") String producerUUID
    ){
        int httpStatus = 200;

        if (!producerUUID.isEmpty()){

            if (DataHandler.readProducersByUUID(producerUUID)!=null){
                DataHandler.deleteProducer(producerUUID);
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
     * updates a new producer
     * @param producerUUID the key
     * @param producer the producer
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateGenre(
            @FormParam("producerUUID") String producerUUID,
            @FormParam("producer") String producer
    ){
        int httpStatus = 200;
        if (!producerUUID.isEmpty()){
            Producer producerObj = DataHandler.readProducersByUUID(producerUUID);
            if (producerObj!=null){
                producerObj.setProducer(producer);
                DataHandler.updateProducer();
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
     * inserts a new producer
     * @param producerUUID the uuid of the genre
     * @param producer the producer
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createFilm(
            @FormParam("producerUUID") String producerUUID,
            @FormParam("producer") String producer
    ){
        int httpStatus = 200;
        Producer producerObj = new Producer();
        producerObj.setProducer(producer);
        DataHandler.updateProducer();

        DataHandler.insertProducer(producerObj);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

}
