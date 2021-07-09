package rs.ac.uns.ftn.uddproject.exception;

public class PlagiarismException extends Exception {

  private static final long serialVersionUID = 3014831684651258077L;

  public PlagiarismException() {
    super("Book is a plagiarism.");
  }
}
