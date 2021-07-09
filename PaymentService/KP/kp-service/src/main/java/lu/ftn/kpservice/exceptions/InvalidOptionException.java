package lu.ftn.kpservice.exceptions;

public class InvalidOptionException extends RuntimeException {

    public InvalidOptionException() {
        super();
    }

    public InvalidOptionException(String message) {
        super(message);
    }
}
