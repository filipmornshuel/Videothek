package ch.bzz.videothek.data;

import ch.bzz.videothek.model.Film;
import ch.bzz.videothek.model.Genre;
import ch.bzz.videothek.model.Producer;
import ch.bzz.videothek.service.Config;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.ws.rs.Produces;
import javax.xml.crypto.Data;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
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
     * sets the diffrent lists in a method
     */
    public static void initLists(){
        DataHandler.setFilmList(null);
        DataHandler.setProducerList(null);
        DataHandler.setGenreList(null);
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
     * inserts a new film into the filmList
     *
     * @param film the film to be saved
     */
    public static void insertFilm(Film film) {
        getFilmList().add(film);
        writeFilmJSON();
    }

    /**
     * updates the filmList
     */
    public static void updateFilm() {
        writeFilmJSON();
    }

    /**
     * deletes a film identified by the filmUUID
     * @param filmUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteFilm(String filmUUID) {
        Film film = readFilmByUUID(filmUUID);
        if (film != null) {
            getFilmList().remove(film);
            writeFilmJSON();
            return true;
        } else {
            return false;
        }
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
     * inserts a new producer into the filmList
     *
     * @param producer the producer to be saved
     */
    public static void insertProducer(Producer producer) {
        getProducerList().add(producer);
        writeProducerJSON();
    }

    /**
     * updates the producerList
     */
    public static void updateProducer() {
        writeProducerJSON();
    }

    /**
     * deletes a producer identified by the producerUUID
     * @param producerUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteProducer(String producerUUID) {
        Producer producer = readProducersByUUID(producerUUID);
        if (producer != null) {
            getProducerList().remove(producer);
            writeProducerJSON();
            return true;
        } else {
            return false;
        }
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
     * inserts a new genre into the genreList
     *
     * @param genre the genre to be saved
     */
    public static void insertGenre(Genre genre) {
        getGenreList().add(genre);
        writeGenreJSON();
    }

    /**
     * updates the genreList
     */
    public static void updateGenre() {
        writeGenreJSON();
    }

    /**
     * deletes a genre identified by the genreUUID
     * @param genreUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteGenre(String genreUUID) {
        Genre genre = readGenresByUUID(genreUUID);
        if (genre != null) {
            getGenreList().remove(genre);
            writeGenreJSON();
            return true;
        } else {
            return false;
        }
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
     * writes the filmList to the JSON-file
     */
    private static void writeFilmJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String filmPath = Config.getProperty("filmJSON");
        try {
            fileOutputStream = new FileOutputStream(filmPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getFilmList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * reads the producer from the JSON-file
     */
    private static void readProducerJSON() {
        try {
            String path = Config.getProperty("producerJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
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
     * writes the producerList to the JSON-file
     */
    private static void writeProducerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String producerPath = Config.getProperty("producerJSON");
        try {
            fileOutputStream = new FileOutputStream(producerPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getProducerList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the genre from the JSON-file
     */
    private static void readGenreJSON() {
        try {
            String path = Config.getProperty("genreJSON");

            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
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
     * writes the genreList to the JSON-file
     */
    private static void writeGenreJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String genrePath = Config.getProperty("genreJSON");
        try {
            fileOutputStream = new FileOutputStream(genrePath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getGenreList());
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