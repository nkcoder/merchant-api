package my.playground.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderReq(@NotNull Long buyerId, @NotEmpty List<OrderItem> items,
                             @NotNull Long shippingAddressId,
                             @NotNull BigDecimal totalAmount,
                             @NotNull LocalDateTime datePlaced) implements Serializable {

}
