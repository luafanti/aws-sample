package pl.lua.aws.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

    private static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    static {
        MAPPER_FACTORY.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));
    }

    public static <S, D> D map(final S sourceObject, final Class<D> destinationClass) {
        return MAPPER_FACTORY.getMapperFacade().map(sourceObject, destinationClass);
    }
    public static <S, D> List<D> mapAsList(final Iterable<S> sourceObject, final Class<D> destinationClass) {
        return MAPPER_FACTORY.getMapperFacade().mapAsList(sourceObject, destinationClass);
    }
}

