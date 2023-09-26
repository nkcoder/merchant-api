package my.playground.product;

import java.math.BigDecimal;
import java.util.Random;
import my.playground.persistence.entity.ProductEntity;

public class ProductMockFactory {

  private static final Random random = new Random();

  public static Product newProduct(String name, String description) {
    return new Product(
        random.nextLong(),
        1L,
        name,
        description,
        BigDecimal.valueOf(100.0),
        10
    );
  }

  public static ProductEntity newProductEntityForReturn(String name, String description) {
    return ProductEntity.builder()
        .id(random.nextLong())
        .sellerId(1L)
        .productName(name)
        .description(description)
        .price(BigDecimal.valueOf(100))
        .quantity(10)
        .build();
  }

  public static ProductEntity newProductEntityForSave(String name, String description) {
    return ProductEntity.builder()
        .sellerId(1L)
        .productName(name)
        .description(description)
        .price(BigDecimal.valueOf(100))
        .quantity(10)
        .build();
  }

}
