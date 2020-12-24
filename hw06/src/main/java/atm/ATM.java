package atm;


import atm.exception.AtmException;
import atm.operation.WithdrawStrategy;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ATM {

    private Map<Denomination, Cell> cells;

    public ATM(List<Cell> cells) {
        this.cells = new TreeMap<>((d1, d2) -> d2.amount - d1.amount);
        this.cells.putAll(cells.stream().collect(Collectors.toMap(Cell::getDenomination, cell -> cell)));
    }

    public Map<Denomination, Cell> getCells() {
        return cells;
    }


    public long getATMBalance() {
        long balance = cells.entrySet().stream().mapToLong(entry -> entry.getKey().amount * entry.getValue().getAmount()).sum();
        System.out.println("Баланс: " + balance);
        return balance;
    }

    public void putMoney(Map<Denomination, Long> payment) {
        for (Denomination denomination : payment.keySet()) {
            cells.get(denomination).addBanknotes(payment.get(denomination));
            System.out.println("Внесено: " + denomination + "*" + payment.get(denomination));
        }
    }

    public Map<Denomination, Long> withdrawMoney(WithdrawStrategy strategy, long requestedAmount) throws AtmException {
        return strategy.withdrawMoney(this, requestedAmount);
    }

}
