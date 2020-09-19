package atm.model.exception;

public class DenominationNotInitializedException extends AtmException {

    public DenominationNotInitializedException() {
        super("Номинал кассеты не указан.");
    }

}
