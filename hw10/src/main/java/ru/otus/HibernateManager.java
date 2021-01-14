package ru.otus;

public interface HibernateManager<T> {

    T create(T objectData);

    T update(T objectData);

    T createOrUpdate(T objectData);

    T load(long id, Class<T> clazz);

}
