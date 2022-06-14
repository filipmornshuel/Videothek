package ch.bzz.videothek.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * custom Serializer for jackson-databind
 */
public class LocalDateSerializer extends StdSerializer<LocalDate> {

    /**
     * constructor
     */
    public LocalDateSerializer() {
        super(LocalDate.class);
    }

    /**
     * converts a data from LocalDate to String
     * @param value
     * @param generator
     * @param provider
     * @throws IOException
     */
    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(value.format(DateTimeFormatter.ISO_DATE));
    }
}
