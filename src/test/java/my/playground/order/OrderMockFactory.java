package my.playground.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static my.playground.product.ProductMockFactory.newProduct;

public class OrderMockFactory {

  private static final Random random = new Random();

  public static Order newOrder(Double totalAmount, String paymentStatus) {
    return new Order(random.nextLong(),
        List.of(newProduct("product1", "description1"), newProduct("product2", "description2")),
        BigDecimal.valueOf(totalAmount), 1L, LocalDateTime.now());
  }

}
