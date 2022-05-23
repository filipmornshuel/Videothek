package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;
import ch.bzz.videothek.model.Film;
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
@Path("producer")
public class ProducerService {
    /**
     * reads a list of all books
     * @return books as JSON
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listProducers() {
        List<Producer> producerList = DataHandler.getInstance().readAllProducers();
        return Response
                .status(200)
                .entity(producerList)
                .build();
    }

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
            Producer producer = DataHandler.getInstance().readProducersByUUID(producerUUID);
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

}
