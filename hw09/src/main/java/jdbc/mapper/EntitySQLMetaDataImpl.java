package jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;


public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private String selectAllSql;
    private String selectByIdSql;
    private String insertSql;
    private String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> metaData) {
        String params = formParamsList(metaData.getAllFields());
        this.selectAllSql = String.format("select %s from %s",
                params,
                metaData.getName());
        this.selectByIdSql = String.format("select %s from %s where %s = ?",
                params,
                metaData.getName(),
                metaData.getIdField().getName());
        this.insertSql = String.format("insert into %s (%s) values (%s)",
                metaData.getName(),
                params,
                formInputPlaceHolder(metaData.getAllFields()));
        this.updateSql = String.format("update %s set %s where %s = ?",
                metaData.getName(),
                formUpdateParamsList(metaData.getFieldsWithoutId()),
                metaData.getIdField().getName());
    }

    private String formInputPlaceHolder(List<Field> fields) {
        return fields.stream()
                .map(f -> "?")
                .collect(Collectors.joining(","));
    }

    private String formParamsList(List<Field> fields) {
        return formParamsWithSuffix(fields, "");
    }

    private String formUpdateParamsList(List<Field> fields) {
        return formParamsWithSuffix(fields, " = ?");
    }

    private String formParamsWithSuffix(List<Field> fields, String suffix) {
        return fields.stream()
                .map(field -> field.getName() + suffix)
                .collect(Collectors.joining(","));
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }

}
