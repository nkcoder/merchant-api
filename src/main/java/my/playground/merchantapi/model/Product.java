package my.playground.merchantapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("products")
@Getter
@RequiredArgsConstructor
public class Product {
    @Id
    @Setter
    private Long productID;
    private final Long sellerID;   // For simplicity, just the ID. Use Join for complex queries.
    private final Long categoryID; // Similarly, just the ID here.
    private final String productName;
    private final String description;
    private final BigDecimal price;
    private final Integer stockQuantity;
    private final String imageURL;
}
