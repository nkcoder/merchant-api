package my.playground.product;

import java.math.BigDecimal;

public record Product(Long productId,
                      Long sellerId,
                      String productName,
                      String description,
                      BigDecimal price,
                      Integer quantity
) {

  public Product withStockUpdated(Integer newStock) {
    return new Product(
        this.productId,
        this.sellerId,
        this.productName,
        this.description,
        this.price,
        newStock
    );
  }

}
