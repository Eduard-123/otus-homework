package core.dao;

import java.util.Optional;

import core.model.User;
import core.sessionmanager.SessionManager;


public interface UserDao {

    Optional<User> findById(long id);

    long insertUser(User user);

    void updateUser(User user);

    long insertOrUpdate(User user);

    SessionManager getSessionManager();
}
