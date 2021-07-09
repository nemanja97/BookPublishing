package lu.ftn.bank1service.exception;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String entity) {
        super(String.format("%s not found.", entity));
    }
}
