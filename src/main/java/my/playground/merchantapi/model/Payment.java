package my.playground.merchantapi.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("payments")
@Getter
public class Payment {
    @Id
    private final Long paymentID;
    private final BigDecimal amount;
    private final String paymentMethod;
    private final LocalDateTime datePaid;
    private final String paymentStatus;

    public Payment(Long paymentID, BigDecimal amount, String paymentMethod, LocalDateTime datePaid, String paymentStatus) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.datePaid = datePaid;
        this.paymentStatus = paymentStatus;
    }
}
