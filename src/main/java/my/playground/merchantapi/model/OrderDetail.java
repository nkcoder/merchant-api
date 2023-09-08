package my.playground.merchantapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("order_details")
@Getter
@RequiredArgsConstructor
public class OrderDetail {
    @Id
    @Setter
    private Long orderDetailId;
    private final Long orderId;
    private final Long productId;
    private final Integer quantity;
    private final BigDecimal subTotal;
}
