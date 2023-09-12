package my.playground.onlineshop.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Payment(BigDecimal amount, String paymentMethod, LocalDateTime datePaid,
                      String paymentStatus) implements Serializable {

}
