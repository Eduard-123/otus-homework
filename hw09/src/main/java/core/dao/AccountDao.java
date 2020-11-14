package core.dao;

import core.model.Account;
import core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {

    Optional<Account> findById(long id);

    long insertAccount(Account account);

    void updateAccount(Account account);

    long insertOrUpdate(Account account);

    SessionManager getSessionManager();

}
