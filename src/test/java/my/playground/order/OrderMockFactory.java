package my.playground.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import my.playground.persistence.entity.OrderEntity;
import my.playground.persistence.entity.OrdersProductsEntity;

public class OrderMockFactory {

  private static final Random random = new Random();

  public static List<OrderEntity> orderEntitiesForReturn(int size) {
    return IntStream.range(0, size).mapToObj(i ->
        OrderEntity.builder()
            .id(random.nextLong())
            .buyerId(random.nextLong())
            .datePlaced(LocalDateTime.now())
            .totalAmount(BigDecimal.valueOf(100 + i))
            .shippingAddressId(random.nextLong())
            .build()
    ).toList();
  }

  public static List<OrdersProductsEntity> ordersProductsEntitiesForReturn(int size) {
    return IntStream.range(0, size).mapToObj(
        i -> OrdersProductsEntity.builder().id((long) i).orderId(1L).productId(2L).build()
    ).toList();
  }

}
