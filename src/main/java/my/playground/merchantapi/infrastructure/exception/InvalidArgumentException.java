package my.playground.merchantapi.infrastructure.exception;

public class InvalidArgumentException extends RuntimeException {

  public InvalidArgumentException() {
    super("Invalid argument.");
  }
}
