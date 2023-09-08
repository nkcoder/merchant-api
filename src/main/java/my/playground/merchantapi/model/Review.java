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
    private Long reviewId;
    private final Long productId;
    private final Long userId;
    private final Integer rating;
    private final String comment;
    private final LocalDateTime datePosted;
}
