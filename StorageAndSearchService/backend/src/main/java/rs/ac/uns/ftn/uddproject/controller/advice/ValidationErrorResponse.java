package rs.ac.uns.ftn.uddproject.controller.advice;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

  private List<Violation> violations = new ArrayList<>();

  List<Violation> getViolations() {
    return violations;
  }

  public void setViolations(List<Violation> violations) {
    this.violations = violations;
  }
}
