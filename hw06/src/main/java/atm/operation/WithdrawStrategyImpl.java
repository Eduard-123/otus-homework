package atm.operation;

import atm.ATM;
import atm.Cell;
import atm.Denomination;
import atm.exception.AtmException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithdrawStrategyImpl implements WithdrawStrategy{

    @Override
    public Map<Denomination, Long> withdrawMoney(ATM atm, long requestedAmount) throws AtmException {
        Map<Denomination, Long> result = new HashMap<>();
        List<Cell> sortedCells = new ArrayList<>(atm.getCells().values());
        long rest = requestedAmount;
        for (Cell cell : sortedCells) {
            long multiplier = rest / cell.getDenomination().amount;
            if (multiplier != 0 && multiplier <= cell.getAmount()) {
                result.put(cell.getDenomination(), multiplier);
                rest -= cell.getDenomination().amount * multiplier;
            }
        }
        if (rest == 0) {
            for (Denomination denomination : result.keySet()) {
                atm.getCells().get(denomination).removeBanknotes(result.get(denomination));
            }
            return result;
        } else {
            throw new AtmException("Запрошенная сумма не может быть выдана");
        }
    }
}
