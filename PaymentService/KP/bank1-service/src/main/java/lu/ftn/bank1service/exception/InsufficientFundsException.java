package lu.ftn.bank1service.exception;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
        super("Not enough funds");
    }
}
