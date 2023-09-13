package my.playground.onlineshop.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderReq(@NotNull Long buyerId, @NotEmpty List<OrderItem> items,
                             @NotNull Payment payment,
                             @NotNull Long billingAddressId,
                             @NotNull Long shippingAddressId,
                             @NotNull LocalDateTime datePlaced) implements Serializable {

}

