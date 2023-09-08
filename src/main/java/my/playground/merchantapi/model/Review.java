package my.playground.merchantapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("reviews")
@Getter
@RequiredArgsConstructor
public class Review {
    @Id
    @Setter
    private Long reviewID;
    private final Long productID;
    private final Long userID;
    private final Integer rating;
    private final String comment;
    private final LocalDateTime datePosted;
}
