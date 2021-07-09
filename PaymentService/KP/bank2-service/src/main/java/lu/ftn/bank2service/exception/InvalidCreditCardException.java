package lu.ftn.bank2service.exception;

public class InvalidCreditCardException extends Exception{

    public InvalidCreditCardException() {
        super("Credit card is invalid");
    }
}
