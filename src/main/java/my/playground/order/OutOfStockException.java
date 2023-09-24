package my.playground.order;

public class OutOfStockException extends RuntimeException {

  public OutOfStockException(String message) {
    super(message);
  }
}
