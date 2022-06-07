package ch.bzz.videothek.model;

import ch.bzz.videothek.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import sun.rmi.server.UnicastServerRef;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A film in our Videothek
 */

public class Film {

    private String filmUUID;
    private String title;
    private Producer producer;
    private Genre genre;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    private BigDecimal price;
    private Integer lenth;
    private String ean;

    /**
     * gets filmUUID
     * @return the filmUUID
     */
    public String getFilmUUID() {
        return filmUUID;
    }

    /**
     * sets filmUUID
     * @param filmUUID the value to set
     */
    public void setFilmUUID(String filmUUID) {
        this.filmUUID = filmUUID;
    }

    /**
     * gets the producerUUID from the Publisher-object
     * @return the producerUUID
     */
    public String getProducerUUID() {
        if (getProducer()== null) return null;
        return getProducer().getProducerUUID();
    }

    /**
     * creates a Producer-object without the filmlist
     * @param producerUUID the key
     */
    public void setProducerUUID(String producerUUID) {
        setProducer(new Producer());
        Producer producer = DataHandler.readProducersByUUID(producerUUID);
        getProducer().setProducerUUID(producerUUID);
        getProducer().setProducer(producer.getProducer());
    }

    /**
     * gets the genreUUID from the Genre-object
     * @return the producerUUID
     */
    public String getGenreUUID() {
        if (getGenre()== null) return null;
        return getGenre().getGenreUUID();
    }

    /**
     * creates a Genre-object without the genreList
     * @param genreUUID the key
     */
    public void setGenreUUID(String genreUUID) {
        setGenre(new Genre());
        Genre genre = DataHandler.readGenresByUUID(genreUUID);
        getGenre().setGenreUUID(genreUUID);
        getGenre().setGenre(genre.getGenre());
    }

    /**
     * gets the title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title
     * @param title the value to set
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * gets the producer
     * @return the producer
     */
    public Producer getProducer() {
        return producer;
    }

    /**
     * sets the producer
     * @param producer the value to set
     */
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    /**
     * gets the price
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * sets the price
     * @param price the value to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    /**
     * gets the publishDate
     * @return the publishDate
     */
    public LocalDate getPublishDate() {
        return publishDate;
    }

    /**
     * sets the publishDate
     * @param publishDate the value to set
     */
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }


    /**
     * gets the lenth
     * @return the lenth
     */
    public Integer getLenth() {
        return lenth;
    }

    /**
     * sets the lenth
     * @param lenth the value to set
     */
    public void setLenth(Integer lenth) {
        this.lenth = lenth;
    }


    /**
     * gets the ean
     * @return the ean
     */
    public String getEan() {
        return ean;
    }

    /**
     * sets the ean
     * @param ean the value to set
     */
    public void setEan(String ean) {
        this.ean = ean;
    }

    /**
     * gets the genre
     * @return the genre
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * sets the genre
     * @param genre the value to set
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
