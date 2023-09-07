package my.playground.merchantapi.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("order_details")
@Getter
public class OrderDetail {
    @Id
    private final Long orderDetailID;
    private final Long orderID;
    private final Long productID;
    private final Integer quantity;
    private final BigDecimal subTotal;

    public OrderDetail(Long orderDetailID, Long orderID, Long productID, Integer quantity, BigDecimal subTotal) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }
}
