package ch.bzz.videothek.model;

/**
 * A genre in our Videothek
 */
public class Genre {

    private String genreUUID;
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
