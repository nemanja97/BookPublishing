package lu.ftn.bank1service.exception;

public class InvoiceAlreadyPaidException extends Exception {

    public InvoiceAlreadyPaidException() {
        super("Invoice already paid");
    }
}
