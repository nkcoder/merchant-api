package my.playground.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import my.playground.product.Product;

public record Order(Long orderId, List<Product> products, Payment payment,
                    BigDecimal totalAmount, Long billingAddressId, Long shippingAddressId,
                    LocalDateTime datePlaced) {

}
