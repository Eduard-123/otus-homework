package atm.model.exception;

public class CassetteIsEmptyException extends AtmException{

    public CassetteIsEmptyException() {
        super("Кассета пуста");
    }

}
