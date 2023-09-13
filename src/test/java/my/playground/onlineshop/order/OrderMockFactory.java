package my.playground.onlineshop.order;

import static my.playground.onlineshop.product.ProductMockFactory.newProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class OrderMockFactory {

  private static final Random random = new Random();

  public static Order newOrder(Double totalAmount, String paymentStatus) {
    return new Order(random.nextLong(),
        List.of(newProduct("product1", "description1"), newProduct("product2", "description2")),
        new Payment(null, BigDecimal.valueOf(totalAmount), "Paypal", LocalDateTime.now(),
            paymentStatus),
        BigDecimal.valueOf(totalAmount), 1L, 2L, LocalDateTime.now());
  }

}
