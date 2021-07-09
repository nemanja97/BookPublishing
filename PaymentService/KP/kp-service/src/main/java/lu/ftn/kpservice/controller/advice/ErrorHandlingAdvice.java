package lu.ftn.kpservice.controller.advice;

import lu.ftn.kpservice.exceptions.EmailAlreadyExistsException;
import lu.ftn.kpservice.exceptions.EntityNotFoundException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice

public class ErrorHandlingAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation("Cause", e.getMessage()));
        return error;
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConversionFailedException(ConversionFailedException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation(e.getValue().toString(), "Unsuported value"));
        return error;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onEmailAlreadyExistsException(EmailAlreadyExistsException e){
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation("email", "Email already taken."));
        return error;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    ValidationErrorResponse onEntityNotFoundException(EntityNotFoundException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation("Cause", e.getMessage()));
        return error;
    }


}
