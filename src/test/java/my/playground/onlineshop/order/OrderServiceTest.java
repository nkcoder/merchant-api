package my.playground.onlineshop.order;

import static my.playground.onlineshop.product.ProductMockFactory.newProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import my.playground.onlineshop.persistence.OrderRepository;
import my.playground.onlineshop.persistence.PaymentRepository;
import my.playground.onlineshop.persistence.entity.OrderEntity;
import my.playground.onlineshop.persistence.entity.PaymentEntity;
import my.playground.onlineshop.product.Product;
import my.playground.onlineshop.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ProductService productService;
  @Mock
  private PaymentRepository paymentRepository;

  @InjectMocks
  private OrderService orderService;

  @Test
  public void shouldCreateOrder() {
    Product mockExistingProduct = newProduct("product", "description");
    when(productService.getProductById(anyLong())).thenReturn(Optional.of(mockExistingProduct));
    Product mockUpdatedProduct = newProduct("product", "description");
    when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(
        mockUpdatedProduct);
    PaymentEntity paymentEntity = new PaymentEntity(BigDecimal.valueOf(10.1), "Paypal",
        LocalDateTime.now(), "Pending");
    paymentEntity.setPaymentId(300L);
    when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);

    OrderEntity orderEntity = new OrderEntity(10L, LocalDateTime.now(), BigDecimal.valueOf(120.0),
        1L, 1L, 300L);
    orderEntity.setOrderId(100L);
    when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

    CreateOrderReq createOrderReq = new CreateOrderReq(1L,
        List.of(new OrderItem(1L, 1), new OrderItem(2L, 1)),
        new Payment(null, BigDecimal.valueOf(120.0), "Paypal", LocalDateTime.now(), "pending"), 1L,
        1L, LocalDateTime.now());
    Order order = orderService.createOrder(createOrderReq);
    assertNotNull(order);
    assertEquals(100L, order.orderId());
  }

  @Test
  public void shouldReturnOrderById() {
    Long orderId = 2L;
    OrderEntity orderEntity = new OrderEntity(2L, LocalDateTime.now(), BigDecimal.valueOf(120.0),
        1L, 1L, 300L);
    PaymentEntity paymentEntity = new PaymentEntity(BigDecimal.valueOf(10.1), "Paypal",
        LocalDateTime.now(), "Pending");
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
    when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(paymentEntity));

    Optional<Order> maybeOrder = orderService.getOrderById(orderId);
    assertTrue(maybeOrder.isPresent());
    assertEquals(BigDecimal.valueOf(120.0), maybeOrder.get().totalAmount());
  }

  @Test
  public void shouldReturnEmptyWhenOrderNotFound() {
    Long orderId = 2L;
    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    Optional<Order> maybeOrder = orderService.getOrderById(orderId);
    assertFalse(maybeOrder.isPresent());
  }
}
