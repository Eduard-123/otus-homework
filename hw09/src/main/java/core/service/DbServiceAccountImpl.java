package core.service;

import core.dao.AccountDao;
import core.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DbServiceAccountImpl implements DbServiceAccount {

    private static final Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

    private final AccountDao accountDao;

    public DbServiceAccountImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public long saveAccount(Account account) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var accountNo = accountDao.insertOrUpdate(account);
                sessionManager.commitSession();
                if (accountNo > 0) {
                    logger.info("created account: {}", accountNo);
                } else {
                    logger.info("updated account");
                }
                return accountNo;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        } catch (DbServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DbServiceException(ex);
        }
    }

    @Override
    public Optional<Account> getAccount(long id) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> accountOptional = accountDao.findById(id);
                logger.info("account: {}", accountOptional.orElse(null));
                return accountOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new DbServiceException(ex);
        }
    }

}
