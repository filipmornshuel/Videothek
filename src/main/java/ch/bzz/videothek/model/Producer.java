package ch.bzz.videothek.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.List;

/**
 * The producer for one or more films
 */

public class Producer {
    @FormParam("producerUUID")
    @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String producerUUID;

    @FormParam("producer")
    @NotEmpty
    @Size(min=3, max=60)
    private String producer;

    @JsonIgnore
    private List<Film> filmList;

    /**
     * gets the producerUUID
     * @return producerUUID
     */
    public String getProducerUUID() {
        return producerUUID;
    }

    /**
     * sets the producerUUID
     * @param producerUUID
     */
    public void setProducerUUID(String producerUUID) {
        this.producerUUID = producerUUID;
    }

    /**
     * gets the producer
     * @return producer
     */
    public String getProducer() {
        return producer;
    }

    /**
     * sets the producer
     * @param producer
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * gets the filmList
     * @return filmList
     */
    public List<Film> getFilmList() {
        return filmList;
    }

    /**
     * sets the filmList
     * @param filmList
     */
    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
    }
}
