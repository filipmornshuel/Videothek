package ch.bzz.videothek.util;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * registers the converter for LocalDate
 */
@Provider
public class LocalDateParamConverterProvider implements ParamConverterProvider {

    /**
     * gets the LocalDateConverter
     * @param rawType
     * @param genericType
     * @param annotations
     * @param <T>
     * @return
     */
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType,
                                              Annotation[] annotations) {
        if (rawType.equals(LocalDate.class))
            return (ParamConverter<T>) new LocalDateConverter();
        return null;
    }

}
