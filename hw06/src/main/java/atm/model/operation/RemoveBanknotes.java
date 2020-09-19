package atm.model.operation;


import atm.model.Denomination;

import java.util.Map;

public interface RemoveBanknotes {
    Map<Denomination, Long> removeBanknotes(long amount);
}
