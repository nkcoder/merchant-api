package my.playground.order;

import my.playground.product.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record Order(Long orderId, List<Product> products,
                    BigDecimal totalAmount, Long shippingAddressId,
                    LocalDateTime datePlaced) {

}
