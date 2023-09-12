package my.playground.onlineshop.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
@Tag(name = "Orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("")
  public ResponseEntity<Order> createOrder(@RequestBody @Valid CreateOrderReq createOrderReq) {
    Order order = orderService.createOrder(createOrderReq);
    return new ResponseEntity<>(order, HttpStatus.CREATED);
  }

}
