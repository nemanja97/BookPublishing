package lu.ftn.bank1service.exception;

public class InvalidCreditCardException extends Exception {

    public InvalidCreditCardException() {
        super("Credit card is invalid");
    }
}
