package my.playground.merchantapi.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("reviews")
@Getter
@RequiredArgsConstructor
public class ReviewEntity {

  @Id
  @Setter
  private Long reviewId;
  private final Long productId;
  private final Long userId;
  private final Integer rating;
  private final String comment;
  private final LocalDateTime datePosted;
}
