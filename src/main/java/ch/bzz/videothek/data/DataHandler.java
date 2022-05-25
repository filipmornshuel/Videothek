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
    private static List<Film> filmList;
    private static List<Producer> producerList;
    private static List<Genre> genreList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        
    }
    

    /**
     * reads all films
     * @return list of films
     */
    public static List<Film> readAllFilms() {
        return getFilmList();
    }

    /**
     * reads a film by its uuid
     * @param filmUUID
     * @return the Film (null=not found)
     */
    public static Film readFilmByUUID(String filmUUID) {
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
    public static List<Producer> readAllProducers() {

        return getProducerList();
    }

    /**
     * reads a producer by its uuid
     * @param producerUUID
     * @return the Producer (null=not found)
     */
    public static Producer readProducersByUUID(String producerUUID) {
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
    public static List<Genre> readAllGenres() {
        return getGenreList();
    }

    /**
     * reads a genre by its uuid
     * @param genreUUID
     * @return the Genre (null=not found)
     */
    public static Genre readGenresByUUID(String genreUUID) {
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
    private static void readFilmJSON() {
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
    private static void readProducerJSON() {
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
    private static void readGenreJSON() {
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
    private static List<Film> getFilmList() {
        if (filmList == null) {
            setFilmList(new ArrayList<>());
            readFilmJSON(); 
        }
        
        return filmList;
    }

    /**
     * sets filmList
     *
     * @param filmList the value to set
     */
    private static void setFilmList(List<Film> filmList) {
        DataHandler.filmList = filmList;
    }

    /**
     * gets producerList
     *
     * @return value of producerList
     */
    private static List<Producer> getProducerList() {
        if (producerList == null) {
            setProducerList(new ArrayList<>());
            readProducerJSON();
        }

       
        return producerList;
    }

    /**
     * sets producerList
     *
     * @param producerList the value to set
     */
    private static void setProducerList(List<Producer> producerList) {
        DataHandler.producerList = producerList;
    }

    /**
     * gets genreList
     *
     * @return value of genreList
     */
    private static List<Genre> getGenreList() {
        if (genreList == null) {
            setGenreList(new ArrayList<>());
            readGenreJSON();
        }
        return genreList;
    }

    /**
     * sets genreList
     *
     * @param genreList the value to set
     */
    private static void setGenreList(List<Genre> genreList) {
        DataHandler.genreList = genreList;
    }

}