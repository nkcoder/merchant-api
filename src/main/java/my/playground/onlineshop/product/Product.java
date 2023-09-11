package my.playground.onlineshop.product;

import java.math.BigDecimal;

public record Product(Long productId,
                      Long sellerId,
                      Long categoryId,
                      String productName,
                      String description,
                      BigDecimal price,
                      Integer stockQuantity,
                      String imageURL) {

}
