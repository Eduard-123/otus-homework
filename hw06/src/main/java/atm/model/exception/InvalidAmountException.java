package atm.model.exception;

public class InvalidAmountException extends AtmException {
    public InvalidAmountException() {
        super("Некорректная сумма");
    }
}
