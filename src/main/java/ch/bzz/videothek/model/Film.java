package ch.bzz.videothek.model;

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
