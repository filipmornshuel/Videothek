package ch.bzz.videothek.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * The producer for one or more films
 */

public class Producer {
    private String producerUUID;
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
