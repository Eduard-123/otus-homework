package jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ObjectConverterImpl<T> implements ObjectConverter<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperEager.class);

    @Override
    public List<Object> extractParamsForInsert(T object, EntityClassMetaData<T> metaData) {
        return extractParams(metaData.getAllFields(), object).collect(Collectors.toList());
    }

    @Override
    public List<Object> extractParamsForUpdate(T object, EntityClassMetaData<T> metaData) {
        return Stream.of(Stream.concat(
                extractParams(metaData.getFieldsWithoutId(), object),
                Stream.of(extractValueFromField(metaData.getIdField(), object))
        )).collect(Collectors.toList());
    }

    @Override
    public T fillObjectForSelect(ResultSet rs, EntityClassMetaData<T> metaData) {
        try {
            T object = metaData.getConstructor().newInstance();
            for (Field field : metaData.getAllFields()) {
                field.setAccessible(true);
                field.set(object, rs.getObject(field.getName()));
            }
            return object;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private Stream<Object> extractParams(List<Field> fields, T object) {
        return fields.stream()
                .map(field -> extractValueFromField(field, object));
    }

    private Object extractValueFromField(Field field, T object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new ru.otus.core.dao.UserDaoException(e);
        }
    }
}
