package my.playground.graphql;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.playground.order.CreateOrderReq;
import my.playground.order.Order;
import my.playground.order.OrderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GraphQLOrderController {

  private final OrderService orderService;

  @QueryMapping
  public Order orderById(@Argument Long id) {
    return orderService.getOrderById(id).orElse(null);
  }

  @QueryMapping
  public List<Order> ordersByUser(@Argument Long userId, @Argument int pageNumber,
      @Argument int pageSize) {
    return orderService.getOrdersByUser(userId, pageNumber, pageSize);
  }

  @MutationMapping(value = "createOrder")
  public Order createOrder(@Argument(value = "createOrderInput") CreateOrderReq createOrderReq) {
    return orderService.createOrder(createOrderReq);
  }

}
