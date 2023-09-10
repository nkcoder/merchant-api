package my.playground.merchantapi.infrastructure.exception;

public class InvalidArgumentException extends RuntimeException {

  public InvalidArgumentException(String message) {
    super(message);
  }
}
