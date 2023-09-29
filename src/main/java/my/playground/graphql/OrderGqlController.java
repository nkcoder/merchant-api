package my.playground.graphql;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.playground.infrastructure.exception.AppException;
import my.playground.order.Order;
import my.playground.order.OrderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderGqlController {

  private final OrderService orderService;

  @QueryMapping
  public Order orderById(@Argument Long id) {
    return orderService.getOrderById(id)
        .orElseThrow(() -> AppException.from(HttpStatus.NOT_FOUND, "order not found: " + id));
  }

  @QueryMapping
  public List<Order> ordersByUser(@Argument Long userId, @Argument int pageNumber,
      @Argument int pageSize) {
    return orderService.getOrdersByUser(userId, pageNumber, pageSize);
  }

}
