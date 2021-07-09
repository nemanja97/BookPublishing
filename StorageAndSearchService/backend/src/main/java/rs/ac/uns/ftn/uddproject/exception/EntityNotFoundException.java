package rs.ac.uns.ftn.uddproject.exception;

public class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 6558370093290801451L;

    public EntityNotFoundException() {
    super();
  }

  public EntityNotFoundException(String entity) {
    super(String.format("%s not found.", entity));
  }
}
