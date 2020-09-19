package atm.model.exception;

public class CashBoxStateIsWrongException extends AtmException {
    public CashBoxStateIsWrongException() {
        super("Неверное состояние кассы");
    }
}
