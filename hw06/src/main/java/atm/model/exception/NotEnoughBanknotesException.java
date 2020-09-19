package atm.model.exception;

public class NotEnoughBanknotesException extends AtmException {

    public NotEnoughBanknotesException() {
        super("Недостаточно банкнот для выдачи средств");
    }

}
