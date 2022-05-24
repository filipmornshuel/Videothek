package ch.bzz.videothek.data;

import ch.bzz.videothek.model.Film;
import ch.bzz.videothek.model.Genre;
import ch.bzz.videothek.model.Producer;
import ch.bzz.videothek.service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Film> filmList;
    private List<Producer> producerList;
    private List<Genre> genreList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setProducerList(new ArrayList<>());
        readProducerJSON();
        setFilmList(new ArrayList<>());
        readFilmJSON();
        setGenreList(new ArrayList<>());
        readGenreJSON();
    }

    /**
     * gets the only instance of this class
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all films
     * @return list of films
     */
    public List<Film> readAllFilms() {
        return getFilmList();
    }

    /**
     * reads a film by its uuid
     * @param filmUUID
     * @return the Film (null=not found)
     */
    public Film readFilmByUUID(String filmUUID) {
        Film film = null;
        for (Film entry : getFilmList()) {
            if (entry.getFilmUUID().equals(filmUUID)) {
                film = entry;
            }
        }
        return film;
    }

    /**
     * reads all Producers
     * @return list of producers
     */
    public List<Producer> readAllProducers() {

        return getProducerList();
    }

    /**
     * reads a producer by its uuid
     * @param producerUUID
     * @return the Producer (null=not found)
     */
    public Producer readProducersByUUID(String producerUUID) {
        Producer producer = null;
        for (Producer entry : getProducerList()) {
            if (entry.getProducerUUID().equals(producerUUID)) {
                producer = entry;
            }
        }
        return producer;
    }

    /**
     * reads all Genres
     * @return list of genres
     */
    public List<Genre> readAllGenres() {
        return getGenreList();
    }

    /**
     * reads a genre by its uuid
     * @param genreUUID
     * @return the Genre (null=not found)
     */
    public Genre readGenresByUUID(String genreUUID) {
        Genre genre = null;
        for (Genre entry : getGenreList()) {
            if (entry.getGenreUUID().equals(genreUUID)) {
                genre = entry;
            }
        }
        return genre;
    }



    /**
     * reads the film from the JSON-file
     */
    private void readFilmJSON() {
        try {
            String path = Config.getProperty("filmJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Film[] films = objectMapper.readValue(jsonData, Film[].class);
            for (Film film : films) {
                getFilmList().add(film);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the producer from the JSON-file
     */
    private void readProducerJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("producerJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Producer[] producers = objectMapper.readValue(jsonData, Producer[].class);
            for (Producer producer : producers) {
                getProducerList().add(producer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the genre from the JSON-file
     */
    private void readGenreJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("genreJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Genre[] genres = objectMapper.readValue(jsonData, Genre[].class);
            for (Genre genre : genres) {
                getGenreList().add(genre);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * gets filmList
     *
     * @return value of filmList
     */
    private List<Film> getFilmList() {
        return filmList;
    }

    /**
     * sets filmList
     *
     * @param filmList the value to set
     */
    private void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
    }

    /**
     * gets producerList
     *
     * @return value of producerList
     */
    private List<Producer> getProducerList() {
        return producerList;
    }

    /**
     * sets producerList
     *
     * @param producerList the value to set
     */
    private void setProducerList(List<Producer> producerList) {
        this.producerList = producerList;
    }

    /**
     * gets genreList
     *
     * @return value of genreList
     */
    private List<Genre> getGenreList() {
        return genreList;
    }

    /**
     * sets genreList
     *
     * @param genreList the value to set
     */
    private void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

}