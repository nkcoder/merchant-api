package my.playground.infrastructure.exception;

public class InvalidArgumentException extends RuntimeException {

  public InvalidArgumentException(String message) {
    super(message);
  }
}
