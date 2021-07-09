package lu.ftn.bank1service.exception;

public class InvalidMerchantInformationException extends Exception {

    public InvalidMerchantInformationException() {
        super("Invalid merchant information");
    }
}
