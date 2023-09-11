package my.playground.onlineshop.infrastructure.exception;

public class InvalidCredentialException extends RuntimeException {

  public InvalidCredentialException(String message) {
    super(message);
  }
}
