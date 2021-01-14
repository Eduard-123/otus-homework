package ru.otus.orm;

import ru.otus.HibernateManager;

import java.util.Optional;

public class DBServiceImpl<T> implements DBService<T> {

    private final Class<T> clazz;
    HibernateManager<T> hibernateManager;

    public DBServiceImpl(Class<T> clazz, HibernateManager<T> hibernateManager) {
        this.clazz = clazz;
        this.hibernateManager = hibernateManager;
    }


    @Override
    public T save(T entity) {
        return hibernateManager.create(entity);
    }

    @Override
    public Optional<T> get(long id) {
        return Optional.of(hibernateManager.load(id, clazz));
    }

    @Override
    public T update(T entity) {
        return hibernateManager.update(entity);
    }
}
