package jdbc.mapper;

import java.sql.ResultSet;
import java.util.List;

public interface ObjectConverter<T> {
    List<Object> extractParamsForInsert(T object, EntityClassMetaData<T> metaData);

    List<Object> extractParamsForUpdate(T object, EntityClassMetaData<T> metaData);

    T fillObjectForSelect(ResultSet rs, EntityClassMetaData<T> metaData);
}
