package my.playground.onlineshop.product;

import java.math.BigDecimal;
import java.util.Random;
import my.playground.onlineshop.persistence.entity.ProductEntity;

public class ProductMockFactory {

  private static final Random random = new Random();

  public static Product newProduct(String name, String description) {
    return new Product(
        random.nextLong(),
        1L,
        2L,
        name,
        description,
        BigDecimal.valueOf(100.0),
        10,
        "https://m.media-amazon.com/images/I/81L8quiJXhL._AC_SL1500_.jpg"
    );
  }

  public static ProductEntity newProductEntity(String name, String description) {
    ProductEntity productEntity = new ProductEntity(
        1L,
        2L,
        name,
        description,
        BigDecimal.valueOf(100.0),
        10,
        "https://m.media-amazon.com/images/I/81L8quiJXhL._AC_SL1500_.jpg"
    );
    productEntity.setProductId(random.nextLong());
    return productEntity;
  }

}
