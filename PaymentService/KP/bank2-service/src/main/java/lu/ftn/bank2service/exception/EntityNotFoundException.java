package lu.ftn.bank2service.exception;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String entity) {
        super(String.format("%s not found.", entity));
    }
}
