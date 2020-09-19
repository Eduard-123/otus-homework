package atm.model.exception;

public class CashBoxIsEmptyException extends AtmException {
    public CashBoxIsEmptyException() {
        super("Касса не должна быть пустой");
    }
}
