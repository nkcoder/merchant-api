package my.playground.persistence.entity;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("products")
@Data
@Builder
public class ProductEntity {

  @Id
  private Long id;
  private Long sellerId;
  private String productName;
  private String description;
  private BigDecimal price;
  private Integer quantity;
}
