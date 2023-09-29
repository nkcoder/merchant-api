package my.playground.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import my.playground.infrastructure.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders")
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("")
  public ResponseEntity<Order> createOrder(@RequestBody @Valid CreateOrderReq createOrderReq) {
    Order order = orderService.createOrder(createOrderReq);
    return new ResponseEntity<>(order, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    Order order = orderService.getOrderById(id)
        .orElseThrow(() -> AppException.from(HttpStatus.NOT_FOUND, "Order not found: " + id));
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<Order>> getOrdersByUser(@RequestParam("userId") Long userId,
      @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    List<Order> orders = orderService.getOrdersByUser(userId, pageNumber, pageSize);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

}
