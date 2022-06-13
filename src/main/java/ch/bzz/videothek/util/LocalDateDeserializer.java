package ch.bzz.videothek.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

/**
 * custom Deserializer for jackson-databind
 */
public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    /**
     * constructor
     */
    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    /**
     * converts a data from String to LocalDate
     * @param parser
     * @param context
     * @return
     * @throws IOException
     */
    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.readValueAs(String.class);
        return LocalDate.parse(value);
    }
}
