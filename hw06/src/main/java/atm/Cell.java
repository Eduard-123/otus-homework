package atm;

import atm.exception.AtmException;
import atm.operation.BanknotesOperation;

public class Cell implements BanknotesOperation {

    private Denomination denomination;
    private long amount;

    public Cell(Denomination denomination,long amount) {
        this.denomination = denomination;
        this.amount = amount;
    }

    @Override
    public void addBanknotes(long number) {
        this.amount += number;
    }

    @Override
    public void removeBanknotes(long number) throws AtmException {
        if (number > amount) {
            throw new AtmException("В ячейке [" + denomination.amount + "] кончились банкноты");
        } else {
            amount -= number;
        }
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }
}
