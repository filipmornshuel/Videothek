package ch.bzz.videothek.model;

/**
 * A genre in our Videothek
 */
public class Genre {


    private String genreUUID;
    private String genre;

    public String getGenreUUID() {
        return genreUUID;
    }

    public void setGenreUUID(String genreUUID) {
        this.genreUUID = genreUUID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
