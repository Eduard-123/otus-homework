package atm.model.exception;

public class UnknownDenominationException extends AtmException {

    public UnknownDenominationException() {
        super("Получены нераспознанные банкноты.");
    }

}
