package my.playground.onlineshop.persistence.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("orders")
@Getter
@RequiredArgsConstructor
public class OrderEntity {

  @Id
  @Setter
  private Long orderId;
  private final Long buyerId;
  private final LocalDateTime datePlaced;
  private final BigDecimal totalAmount;
  private final Long shippingAddressId;
  private final Long billingAddressId;
  private final Long paymentId;
}
