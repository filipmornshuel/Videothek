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

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A film in our Videothek
 */

public class Film {
    //@Pattern(regexp="[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")

    @FormParam("filmUUID")
    @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String filmUUID;

    @FormParam("title")
    @NotEmpty
    @Size(min=2, max=40)
    private String title;

    @FormParam("producerUUID")
    @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String producerUUID;

    @FormParam("genreUUID")
    @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String genreUUID;


    @JsonIgnore
    private Producer producer;

    @JsonIgnore
    private Genre genre;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    @FormParam("price")
    @DecimalMax(value="100.00")
    @DecimalMin(value="0.05")
    private BigDecimal price;

    @FormParam("lenth")
    @DecimalMin(value = "5")
    @DecimalMax(value = "400")
    private Integer lenth;

    @FormParam("ean")
    @NotEmpty
    @Pattern(regexp = "[4-5]{1}[0-7]{3}[0-4]{3}[0-9]{6}")
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
     * sets the publishDate with a string
     * @param publish
     */
    public void setPublishDateWithString(String publish){
        this.publishDate = LocalDate.parse(publish);
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
