package my.playground.merchantapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("payments")
@Getter
@RequiredArgsConstructor
public class Payment {
    @Id
    @Setter
    private Long paymentID;
    private final BigDecimal amount;
    private final String paymentMethod;
    private final LocalDateTime datePaid;
    private final String paymentStatus;
}
