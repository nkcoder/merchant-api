package my.playground.product;

import my.playground.persistence.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.Random;

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
    ProductEntity productEntity = newProductEntityForSave(name, description);
    productEntity.setId(random.nextLong());
    return productEntity;
  }

  public static ProductEntity newProductEntityForSave(String name, String description) {
    return new ProductEntity(
        1L,
        name,
        description,
        BigDecimal.valueOf(100.0),
        10
    );
  }

}
