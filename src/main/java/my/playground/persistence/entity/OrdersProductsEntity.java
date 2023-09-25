package my.playground.persistence.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders_products")
@Getter
@RequiredArgsConstructor
public class OrdersProductsEntity {

  private final Long orderId;
  private final Long productId;
  @Setter
  @Id
  private Long id;
}
