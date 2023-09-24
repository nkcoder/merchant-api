package my.playground.product;

import java.math.BigDecimal;

public record Product(Long productId,
                      Long sellerId,
                      Long categoryId,
                      String productName,
                      String description,
                      BigDecimal price,
                      Integer stockQuantity,
                      String imageURL) {

  public Product withStockUpdated(Integer newStock) {
    return new Product(
        this.productId,
        this.sellerId,
        this.categoryId,
        this.productName,
        this.description,
        this.price,
        newStock,
        this.imageURL
    );
  }

}
