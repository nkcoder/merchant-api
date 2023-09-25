package my.playground.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        .orElseThrow(() -> new OrderNotFoundException("orderId: " + id));
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

}
