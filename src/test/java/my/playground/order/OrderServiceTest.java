package my.playground.order;

import my.playground.persistence.OrderRepository;
import my.playground.persistence.OrdersProductsRepository;
import my.playground.persistence.entity.OrderEntity;
import my.playground.persistence.entity.OrdersProductsEntity;
import my.playground.product.Product;
import my.playground.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static my.playground.product.ProductMockFactory.newProduct;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ProductService productService;
  @Mock
  private OrdersProductsRepository ordersProductsRepository;

  @InjectMocks
  private OrderService orderService;

  @Test
  public void shouldCreateOrder() {
    Product mockExistingProduct = newProduct("product", "description");
    when(productService.getProductById(anyLong())).thenReturn(Optional.of(mockExistingProduct));
    Product mockUpdatedProduct = newProduct("product", "description");
    when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(
        mockUpdatedProduct);

    OrderEntity orderEntity = new OrderEntity(10L, LocalDateTime.now(), BigDecimal.valueOf(120.0),
        1L);
    orderEntity.setId(100L);
    when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
    when(ordersProductsRepository.saveAll(anyList())).thenReturn(List.of());

    CreateOrderReq createOrderReq = new CreateOrderReq(1L,
        List.of(new OrderItem(1L, 1), new OrderItem(2L, 1)), 1L, BigDecimal.valueOf(200),
        LocalDateTime.now());
    Order order = orderService.createOrder(createOrderReq);
    assertNotNull(order);
    assertEquals(100L, order.orderId());
  }

  @Test
  public void shouldReturnOrderById() {
    Long orderId = 2L;
    List<OrdersProductsEntity> ordersProductsEntities = List.of(
        new OrdersProductsEntity(orderId, 1L),
        new OrdersProductsEntity(orderId, 2L)
    );
    when(ordersProductsRepository.findAllByOrderId(orderId)).thenReturn(ordersProductsEntities);

    List<Product> products = List.of(
        new Product(1L, 1L, "product1", "description1", BigDecimal.valueOf(100), 20),
        new Product(2L, 2L, "product2", "description2", BigDecimal.valueOf(200), 20)
    );
    when(productService.getProducts(List.of(1L, 2L))).thenReturn(products);

    OrderEntity orderEntity = new OrderEntity(2L, LocalDateTime.now(), BigDecimal.valueOf(120.0), 1L);
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));

    Optional<Order> maybeOrder = orderService.getOrderById(orderId);
    assertTrue(maybeOrder.isPresent());
    assertEquals(maybeOrder.get().products().size(), 2);
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
