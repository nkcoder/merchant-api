package my.playground.persistence.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("products")
@Getter
@RequiredArgsConstructor
public class ProductEntity {

  private final Long sellerId;
  private final String productName;
  private final String description;
  private final BigDecimal price;
  private final Integer quantity;
  @Id
  @Setter
  private Long id;
}
