package service;

import service.entities.User;

import java.util.List;

public interface DBUserService {

    void create(User object);

    void update(User object);

    User load(Long id);

    List<User> loadAll();
}
