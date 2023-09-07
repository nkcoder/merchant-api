package my.playground.merchantapi.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("products")
@Getter
public class Product {
    @Id
    private final Long productID;
    private final Long sellerID;   // For simplicity, just the ID. Use Join for complex queries.
    private final Long categoryID; // Similarly, just the ID here.
    private final String productName;
    private final String description;
    private final BigDecimal price;
    private final Integer stockQuantity;
    private final String imageURL;

    public Product(Long productID, Long sellerID, Long categoryID, String productName, String description, BigDecimal price, Integer stockQuantity, String imageURL) {
        this.productID = productID;
        this.sellerID = sellerID;
        this.categoryID = categoryID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageURL = imageURL;
    }
}
