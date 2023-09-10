package my.playground.merchantapi.infrastructure.exception;

public class InvalidCredentialException extends RuntimeException {

  public InvalidCredentialException(String message) {
    super(message);
  }
}
