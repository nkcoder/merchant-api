package my.playground.persistence.entity;

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

  private final Long buyerId;
  private final LocalDateTime datePlaced;
  private final BigDecimal totalAmount;
  private final Long shippingAddressId;
  @Id
  @Setter
  private Long id;
}
