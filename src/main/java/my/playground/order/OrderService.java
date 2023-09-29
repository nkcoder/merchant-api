package my.playground.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import my.playground.infrastructure.exception.AppException;
import my.playground.persistence.OrderRepository;
import my.playground.persistence.OrdersProductsRepository;
import my.playground.persistence.entity.OrderEntity;
import my.playground.persistence.entity.OrdersProductsEntity;
import my.playground.product.Product;
import my.playground.product.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductService productService;
  private final OrdersProductsRepository ordersProductsRepository;

  @Transactional
  public Order createOrder(CreateOrderReq createOrderReq) {
    List<Product> orderProducts = updateProductStocks(createOrderReq.items());

    OrderEntity orderEntity = buildOrderEntityFromRequest(createOrderReq);
    OrderEntity savedOrder = orderRepository.save(orderEntity);
    List<OrdersProductsEntity> ordersProducts = buildOrdersProducts(savedOrder.getId(),
        createOrderReq);
    ordersProductsRepository.saveAll(ordersProducts);

    return mapToOrderDomain(savedOrder, orderProducts);
  }

  private List<Product> updateProductStocks(List<OrderItem> orderItems) {
    List<Product> orderProducts = new ArrayList<>();
    for (OrderItem item : orderItems) {
      Product product = productService.getProductById(item.productId()).orElseThrow(
          () -> AppException.from(HttpStatus.NOT_FOUND, "Product not found: " + item.productId()));
      if (product.quantity() < item.quantity()) {
        throw AppException.from(HttpStatus.BAD_REQUEST,
            "Product out of stock: " + item.productId());
      }
      Product updatedProduct = productService.updateProduct(item.productId(),
          product.withStockUpdated(product.quantity() - item.quantity()));
      orderProducts.add(updatedProduct);
    }
    return orderProducts;
  }

  private OrderEntity buildOrderEntityFromRequest(CreateOrderReq createOrderReq) {
    return OrderEntity.builder().buyerId(createOrderReq.buyerId())
        .datePlaced(createOrderReq.datePlaced()).totalAmount(createOrderReq.totalAmount())
        .shippingAddressId(createOrderReq.shippingAddressId()).build();
  }

  private List<OrdersProductsEntity> buildOrdersProducts(Long orderId,
      CreateOrderReq createOrderReq) {
    return createOrderReq.items().stream().map(
            item -> OrdersProductsEntity.builder().orderId(orderId).productId(item.productId()).build())
        .toList();
  }

  private Order mapToOrderDomain(OrderEntity savedOrder, List<Product> orderProducts) {
    return new Order(savedOrder.getId(), orderProducts, savedOrder.getTotalAmount(),
        savedOrder.getShippingAddressId(), savedOrder.getDatePlaced());
  }

  public Optional<Order> getOrderById(Long orderId) {
    return orderRepository.findById(orderId).map(
        oe -> new Order(oe.getId(), getProductsByOrderId(orderId), oe.getTotalAmount(),
            oe.getShippingAddressId(),
            oe.getDatePlaced()));
  }

  public List<Order> getOrdersByUser(Long userId, int pageNumber, int pageSize) {
    return orderRepository.findAllByBuyerId(userId,
            PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "datePlaced"))).stream()
        .map(orderEntity -> {
          List<Product> products = getProductsByOrderId(orderEntity.getId());
          return new Order(
              orderEntity.getId(),
              products,
              orderEntity.getTotalAmount(),
              orderEntity.getShippingAddressId(),
              orderEntity.getDatePlaced()
          );
        }).toList();
  }

  private List<Product> getProductsByOrderId(Long orderId) {
    List<Long> productIds = ordersProductsRepository.findAllByOrderId(orderId).stream()
        .map(OrdersProductsEntity::getProductId).toList();
    return productService.getProducts(productIds);
  }
}
