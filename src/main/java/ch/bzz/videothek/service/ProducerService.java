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
    public Response listProducers(
            @CookieParam("role") String userRole
    ) {
        int httpStatus = 200;
        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole==null||userRole.equals("guest")){
            httpStatus = 403;
        }
        List<Producer> producerList = DataHandler.readAllProducers();
        return Response
                .status(httpStatus)
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
            @QueryParam("producerUUID") String producerUUID,
            @CookieParam("role") String userRole
    ){
        Producer producer = null;
        int httpStatus = 200;
        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole==null||userRole.equals("guest")){
            httpStatus = 403;
        }
        else {
            try {
                producer = DataHandler.readProducersByUUID(producerUUID);
                if (producer == null){
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
            @QueryParam("producerUUID") String producerUUID,
            @CookieParam("role") String userRole
    ){
        int httpStatus = 200;
        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole != null && userRole.equals("admin")){
            if (!DataHandler.deleteProducer(producerUUID)){
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
     * updates a new producer
     * @param producer the producer
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateProducer(
            @Valid @BeanParam Producer producer,
            @CookieParam("role") String userRole
    ){
        int httpStatus = 200;
        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }
        if (userRole != null && userRole.equals("admin")){

            Producer oldProducer = DataHandler.readProducersByUUID(producer.getProducerUUID());
            if (oldProducer!=null){
                oldProducer.setProducer(producer.getProducer());
                oldProducer.setFilmList(producer.getFilmList());
                DataHandler.updateProducer();
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
     * inserts a new producer
     * @param producer the producer
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createProducer(
            @Valid @BeanParam Producer producer,
            @CookieParam("role") String userRole
    ){
        int httpStatus = 200;
        if (userRole!=null){
            userRole = AESEncrypt.decrypt(userRole);
        }

        if (userRole != null && userRole.equals("admin")){
            producer.setProducerUUID(UUID.randomUUID().toString());
            DataHandler.insertProducer(producer);
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
