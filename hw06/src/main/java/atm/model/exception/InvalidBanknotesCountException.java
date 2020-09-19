package atm.model.exception;

public class InvalidBanknotesCountException extends AtmException {
    public InvalidBanknotesCountException() {
        super("Некорректное количество банкнот");
    }
}
