package ch.bzz.videothek.util;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDate;

/**
 * converts @Formparam of type LocalDate from/to String
 */
public class LocalDateConverter implements ParamConverter<LocalDate> {

    /**
     * converts String to LocalDate
     * @param value  the String
     * @return LocalDate
     */
    @Override
    public LocalDate fromString(String value) {
        if (value == null)
            return null;
        return LocalDate.parse(value);
    }

    /**
     * converts LocalDate to String
     * @param value  the LocalDate
     * @return String
     */
    @Override
    public String toString(LocalDate value) {
        if (value == null)
            return null;
        return value.toString();
    }

}