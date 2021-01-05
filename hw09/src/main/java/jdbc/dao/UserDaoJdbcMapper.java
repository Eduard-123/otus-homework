package jdbc.dao;

import core.dao.UserDao;
import core.model.User;
import core.sessionmanager.SessionManager;
import jdbc.mapper.JdbcMapper;
import jdbc.sessionmanager.SessionManagerJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserDaoJdbcMapper implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbcMapper.class);

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<User> jdbcMapper;

    public UserDaoJdbcMapper(SessionManagerJdbc sessionManager, JdbcMapper<User> jdbcMapper) {
        this.sessionManager = sessionManager;
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            return jdbcMapper.findById(id, User.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insertUser(User user) {
        try {
            return jdbcMapper.insert(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ru.otus.core.dao.UserDaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        jdbcMapper.update(user);
    }

    @Override
    public long insertOrUpdate(User user) {
        return jdbcMapper.insertOrUpdate(user);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
