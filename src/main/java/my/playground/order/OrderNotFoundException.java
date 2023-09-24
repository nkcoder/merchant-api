package my.playground.order;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(String message) {
    super(message);
  }
}
