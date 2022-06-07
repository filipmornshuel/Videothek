package ch.bzz.videothek.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * A genre in our Videothek
 */
public class Genre {
    @FormParam("genreUUID")
    @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String genreUUID;

    @FormParam("genre")
    @NotEmpty
    @Size(min=3, max=80)
    private String genre;

    /**
     * gets genreUUID
     * @return genreUUID
     */
    public String getGenreUUID() {
        return genreUUID;
    }

    /**
     * sets genreUUID
     * @param genreUUID
     */
    public void setGenreUUID(String genreUUID) {
        this.genreUUID = genreUUID;
    }

    /**
     * gets genre
     * @return genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * sets genre
     * @param genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
}
