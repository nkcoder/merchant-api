package my.playground.onlineshop.order;

import my.playground.onlineshop.product.ProductService;
import my.playground.onlineshop.persistence.OrderRepository;
import my.playground.onlineshop.user.UserService;
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
  private UserService userService;

  @InjectMocks
  private OrderService orderService;


}
