package my.playground.order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import my.playground.IntegrationBaseTest;
import my.playground.persistence.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class OrderControllerTest extends IntegrationBaseTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @AfterEach
  public void teardown() {
    userRepository.deleteById(userId);
    orderRepository.deleteAll();
  }

  @Test
  public void shouldCreateOrder() {
    CreateOrderReq createOrderReq = new CreateOrderReq(
        1L, List.of(new OrderItem(1L, 1), new OrderItem(2L, 1)),
        1L, BigDecimal.valueOf(300), LocalDateTime.now()
    );

    given()
        .header("Authorization", generateJwtToken())
        .body(createOrderReq)
        .contentType(ContentType.JSON)
        .when()
        .post("/v1/orders")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("products", hasSize(2));
  }

  @Test
  public void shouldGetOrderById() {
    CreateOrderReq createOrderReq = new CreateOrderReq(
        1L, List.of(new OrderItem(1L, 1), new OrderItem(2L, 1)),
        1L, BigDecimal.valueOf(300), LocalDateTime.now()
    );
    Order order = orderService.createOrder(createOrderReq);

    given()
        .header("Authorization", generateJwtToken())
        .when()
        .get("/v1/orders/" + order.orderId())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("products", hasSize(2));
  }

  @Test
  public void shouldThrowExceptionWhenOrderNotFound() {
    given()
        .header("Authorization", generateJwtToken())
        .when()
        .get("/v1/orders/" + Long.MAX_VALUE)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void shouldReturnOrdersOfUser() {
    List<CreateOrderReq> createOrders = List.of(new CreateOrderReq(
            1L, List.of(new OrderItem(1L, 1), new OrderItem(2L, 1)),
            1L, BigDecimal.valueOf(300), LocalDateTime.now()
        ), new CreateOrderReq(
            1L, List.of(new OrderItem(1L, 3), new OrderItem(2L, 4)),
            2L, BigDecimal.valueOf(800), LocalDateTime.now()
        )
    );
    createOrders.forEach(orderService::createOrder);

    given()
        .header("Authorization", generateJwtToken())
        .when()
        .get("/v1/orders?userId=1")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("", hasSize(2));
  }

}
