package my.playground.merchantapi.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("orders")
@Getter
public class Order {
    @Id
    private final Long orderID;
    private final Long buyerID;
    private final LocalDateTime datePlaced;
    private final BigDecimal totalAmount;
    private final Long shippingAddressID;
    private final Long billingAddressID;
    private final Long paymentID;

    public Order(Long orderID, Long buyerID, LocalDateTime datePlaced, BigDecimal totalAmount, Long shippingAddressID, Long billingAddressID, Long paymentID) {
        this.orderID = orderID;
        this.buyerID = buyerID;
        this.datePlaced = datePlaced;
        this.totalAmount = totalAmount;
        this.shippingAddressID = shippingAddressID;
        this.billingAddressID = billingAddressID;
        this.paymentID = paymentID;
    }
}
