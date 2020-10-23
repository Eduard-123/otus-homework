package jdbc;

public interface DbExecutor<T> {

    void create(T objectData) throws Exception;

    void update(T objectData) throws Exception;

    T load(long id, Class<T> clazz) throws Exception;
}
