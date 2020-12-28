package atm.operation;

import atm.exception.AtmException;

public interface BanknotesOperation {

    void addBanknotes(long number);

    void removeBanknotes(long number) throws AtmException;

}
