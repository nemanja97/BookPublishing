package lu.ftn.bank2service.exception;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
        super("Not enough funds");
    }
}
