package lu.ftn.bankpaymentservice.controller.advice;

import lu.ftn.bankpaymentservice.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ValidationErrorResponse onEntityNotFoundException(EntityNotFoundException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation("Entity not found error", e.getMessage()));
        return error;
    }

}
