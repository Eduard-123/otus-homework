package jdbc.mapper;

import jdbc.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.empty;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private String name;
    private Constructor<T> constructor;
    private Field idField;
    private List<Field> allFields;
    private List<Field> fieldsWithoutId;


    public EntityClassMetaDataImpl(Class<T> tClass) {
        throwExceptionIfNull(tClass);
        this.name = tClass.getSimpleName();
        this.constructor = findDefaultConstructor(tClass);
        this.allFields = findAllField(tClass);
        this.fieldsWithoutId = allFields.stream()
                .filter(field -> !haveIdAnnotation(field))
                .collect(Collectors.toList());
        this.idField = allFields.stream()
                .filter(this::haveIdAnnotation)
                .findAny()
                .orElseThrow();
    }

    private boolean haveIdAnnotation(Field field) {
        return field.getDeclaredAnnotation(Id.class) != null;
    }

    private void throwExceptionIfNull(Class<T> tClass) {
        if (tClass == null) {
            throw new IllegalArgumentException("Class not initialized");
        }
    }

    private Constructor<T> findDefaultConstructor(Class<T> tClass) {
        return (Constructor<T>) Stream.of(tClass.getDeclaredConstructors())
                .filter(constructor -> !constructor.isVarArgs())
                .filter(constructor -> Modifier.isPublic(constructor.getModifiers()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Constructor didn't find"));
    }

    private List<Field> findAllField(Class<T> tClass) {
        return findAllFieldStream(tClass).collect(Collectors.toList());
    }

    private Stream<Field> findAllFieldStream(Class<?> tClass) {
        if (tClass.equals(Object.class)) {
            return empty();
        }
        return Stream.concat(
                Stream.of(tClass.getDeclaredFields()),
                findAllFieldStream(tClass.getSuperclass())
        );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
