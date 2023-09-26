package my.playground.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders_products")
@Data
@Builder
public class OrdersProductsEntity {

  @Id
  private Long id;
  private Long orderId;
  private Long productId;
}
