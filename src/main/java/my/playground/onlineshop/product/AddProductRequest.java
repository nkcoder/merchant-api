package my.playground.onlineshop.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record AddProductRequest(@NotNull Long sellerId, @NotNull Long categoryId,
                                @NotEmpty @Size(min = 3, max = 20) String productName,
                                @NotEmpty @Size(min = 5, max = 255) String description,
                                @NotNull BigDecimal price, @NotNull Integer stockQuantity,
                                @NotEmpty String imageURL) {

}
