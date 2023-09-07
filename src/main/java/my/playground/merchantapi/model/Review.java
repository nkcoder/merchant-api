package my.playground.merchantapi.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("reviews")
@Getter
public class Review {
    @Id
    private final Long reviewID;
    private final Long productID;
    private final Long userID;
    private final Integer rating;
    private final String comment;
    private final LocalDateTime datePosted;

    public Review(Long reviewID, Long productID, Long userID, Integer rating, String comment, LocalDateTime datePosted) {
        this.reviewID = reviewID;
        this.productID = productID;
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
        this.datePosted = datePosted;
    }
}
