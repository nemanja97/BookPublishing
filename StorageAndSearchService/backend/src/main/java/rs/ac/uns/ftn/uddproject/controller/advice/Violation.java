package rs.ac.uns.ftn.uddproject.controller.advice;

public class Violation {

  private final String fieldName;

  private final String message;

  Violation(String fieldName, String message) {
    this.fieldName = fieldName;
    this.message = message;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getMessage() {
    return message;
  }
}
