package my.playground.onlineshop.order;

import static my.playground.onlineshop.order.OrderMockFactory.newOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import my.playground.onlineshop.infrastructure.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class OrderControllerTest {

  @MockBean
  private OrderService orderService;

  @Autowired
  private JsonUtil jsonUtil;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldCreateOrder() throws Exception {
    Order mockOrder = newOrder(120.5, "pending");
    when(orderService.createOrder(any(CreateOrderReq.class))).thenReturn(mockOrder);

    CreateOrderReq createOrderReq = new CreateOrderReq(
        1L, List.of(new OrderItem(1L, 1), new OrderItem(2L, 1)),
        new Payment(BigDecimal.valueOf(120.0), "Paypal", LocalDateTime.now(), "pending"),
        BigDecimal.valueOf(120.0), 1L, 1L, LocalDateTime.now()
    );

    System.out.println("creatOrderReq: " + jsonUtil.toJson(createOrderReq));

    mockMvc.perform(post("/v1/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonUtil.toJson(createOrderReq)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.totalAmount").value(120.5))
        .andExpect(jsonPath("$.payment.paymentStatus").value("pending"));
  }

}
