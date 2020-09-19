package atm.model.exception;

public abstract class AtmException extends RuntimeException {
    public AtmException(String message) {
        super(message);
    }

    public AtmException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
