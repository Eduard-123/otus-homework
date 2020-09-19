package atm.model.exception;

public class CassetteStateIsWrongException extends AtmException {

    public CassetteStateIsWrongException() {
        super("Неверное состояние кассеты");
    }

    public CassetteStateIsWrongException(Throwable ex) {
        super("Неверное состояние кассеты", ex);
    }
}
