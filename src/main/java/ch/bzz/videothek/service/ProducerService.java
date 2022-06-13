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
import java.nio.channels.ScatteringByteChannel;
import java.util.List;
import java.util.UUID;

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
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String producerUUID
    ){
        Producer producer = null;
        int status;
        try {
            producer = DataHandler.readProducersByUUID(producerUUID);
            if (producer == null){
                status = 404;
            }else {
                status = 200;
            }
        }catch (IllegalArgumentException argEx){
            status = 400;
        }

        return Response
                .status(status)
                .entity(producer)
                .build();
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
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String producerUUID
    ){
        int httpStatus = 200;

        if (!DataHandler.deleteProducer(producerUUID)){
            httpStatus = 410;
        }


        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates a new producer
     * @param producer the producer
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateProducer(
            @Valid @BeanParam Producer producer
    ){
        int httpStatus = 200;
        Producer oldProducer = DataHandler.readProducersByUUID(producer.getProducerUUID());
        if (oldProducer!=null){
            oldProducer.setProducer(producer.getProducer());

            oldProducer.setFilmList(producer.getFilmList());

            DataHandler.updateProducer();
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
     * inserts a new producer
     * @param producer the producer
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createProducer(
            @Valid @BeanParam Producer producer
    ){
        int httpStatus = 200;
        producer.setProducerUUID(UUID.randomUUID().toString());

        DataHandler.insertProducer(producer);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }


}
