package ch.bzz.videothek.service;

import ch.bzz.videothek.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TestService class
 */
@Path("test")
public class TestService {

    /**
     * a status test
     * @return response as JSON
     */
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }

    /**
     * restores the json-files
     * @return Response
     */
    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response restore() {
        try {
            java.nio.file.Path path = Paths.get(Config.getProperty("filmJSON"));
            String filename = path.getFileName().toString();
            String folder = path.getParent().toString();

            byte[] filmJSON = Files.readAllBytes(Paths.get(folder, "Backups", filename));
            FileOutputStream fileOutputStream = new FileOutputStream(Config.getProperty("filmJSON"));
            fileOutputStream.write(filmJSON);


            path = Paths.get(Config.getProperty("producerJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] producerJSON = Files.readAllBytes(Paths.get(folder, "Backups", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("producerJSON"));
            fileOutputStream.write(producerJSON);


            path = Paths.get(Config.getProperty("genreJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] genreJSON = Files.readAllBytes(Paths.get(folder, "Backups", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("genreJSON"));
            fileOutputStream.write(genreJSON);



        } catch (IOException e) {
            e.printStackTrace();
        }



        DataHandler.initLists();
        return Response
                .status(200)
                .entity("Erfolgreich")
                .build();
    }
}


