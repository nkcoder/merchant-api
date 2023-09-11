package my.playground.onlineshop.infrastructure.exception;

public class InvalidArgumentException extends RuntimeException {

  public InvalidArgumentException(String message) {
    super(message);
  }
}
