package ch.bzz.videothek.model;

import java.util.List;

/**
 * Der Produzent von einem oder mehreren Filmen
 */

public class Producer {
    private String producerUUID;
    private String producer;
    private List<Film> filmList;

    public String getProducerUUID() {
        return producerUUID;
    }

    public void setProducerUUID(String producerUUID) {
        this.producerUUID = producerUUID;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
    }
}
