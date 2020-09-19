package atm.model.operation;

import atm.model.Denomination;

import java.util.Map;

public interface AddBanknotes {
    void addBanknotes(Map<Denomination, Long> banknotes);
}
