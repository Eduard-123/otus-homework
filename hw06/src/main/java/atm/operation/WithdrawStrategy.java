package atm.operation;

import atm.ATM;
import atm.Denomination;
import atm.exception.AtmException;

import java.util.Map;

public interface WithdrawStrategy {

    Map<Denomination,Long> withdrawMoney(ATM atm, long requestedAmount) throws AtmException;

}
