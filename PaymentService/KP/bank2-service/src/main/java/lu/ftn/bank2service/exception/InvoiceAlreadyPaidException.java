package lu.ftn.bank2service.exception;

public class InvoiceAlreadyPaidException extends Exception {

    public InvoiceAlreadyPaidException() {
        super("Invoice already paid");
    }
}
