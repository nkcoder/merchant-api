package my.playground.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
@Data
@Builder
public class OrderEntity {

  @Id
  private Long id;
  private Long buyerId;
  private LocalDateTime datePlaced;
  private BigDecimal totalAmount;
  private Long shippingAddressId;
}
