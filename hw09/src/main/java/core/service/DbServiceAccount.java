package core.service;

import core.model.Account;

import java.util.Optional;

public interface DbServiceAccount {


    long saveAccount(Account account);

    Optional<Account> getAccount(long id);

}
