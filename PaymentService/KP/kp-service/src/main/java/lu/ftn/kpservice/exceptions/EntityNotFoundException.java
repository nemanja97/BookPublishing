package lu.ftn.kpservice.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String entity) {
        super(String.format("%s not found.", entity));
    }

}

