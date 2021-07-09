package rs.ac.uns.ftn.uddproject.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rs.ac.uns.ftn.uddproject.exception.EntityNotFoundException;
import rs.ac.uns.ftn.uddproject.exception.PlagiarismException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorHandlingAdvice {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  static ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (ConstraintViolation violation : e.getConstraintViolations())
      error
          .getViolations()
          .add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
    return error;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  static ValidationErrorResponse onMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors())
      error
          .getViolations()
          .add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
    return error;
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  static ValidationErrorResponse onHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    error.getViolations().add(new Violation("Cause", e.getMessage()));
    return error;
  }

  @ExceptionHandler(PlagiarismException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  static ValidationErrorResponse onPlagiarismException(PlagiarismException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    error.getViolations().add(new Violation("book", "Book is a plagiarism"));
    return error;
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ResponseBody
  static ValidationErrorResponse onEntityNotFoundException(EntityNotFoundException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    error.getViolations().add(new Violation("Cause", e.getMessage()));
    return error;
  }
}
