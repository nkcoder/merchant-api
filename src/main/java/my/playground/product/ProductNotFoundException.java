package my.playground.product;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(String message) {
    super(message);
  }
}
