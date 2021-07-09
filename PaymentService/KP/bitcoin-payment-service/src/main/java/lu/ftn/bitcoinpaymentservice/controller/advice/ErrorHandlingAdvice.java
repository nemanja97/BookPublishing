package lu.ftn.bitcoinpaymentservice.controller.advice;

import com.bitpay.sdk_light.BitPayException;
import lu.ftn.bitcoinpaymentservice.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(BitPayException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    ValidationErrorResponse onBitPayException(BitPayException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation("BitPay communication error", e.getMessage()));
        return error;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ValidationErrorResponse onEntityNotFoundException(EntityNotFoundException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation("Entity not found error", e.getMessage()));
        return error;
    }

}
