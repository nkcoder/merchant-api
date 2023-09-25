package my.playground.order;

import lombok.RequiredArgsConstructor;
import my.playground.persistence.OrderRepository;
import my.playground.persistence.OrdersProductsRepository;
import my.playground.persistence.entity.OrderEntity;
import my.playground.persistence.entity.OrdersProductsEntity;
import my.playground.product.Product;
import my.playground.product.ProductNotFoundException;
import my.playground.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
      Product product = productService.getProductById(item.productId())
          .orElseThrow(() -> new ProductNotFoundException("productId: " + item.productId()));
      if (product.quantity() < item.quantity()) {
        throw new OutOfStockException("productId: " + item.productId());
      }
      Product updatedProduct = productService.updateProduct(item.productId(),
          product.withStockUpdated(product.quantity() - item.quantity()));
      orderProducts.add(updatedProduct);
    }
    return orderProducts;
  }

  private OrderEntity buildOrderEntityFromRequest(CreateOrderReq createOrderReq) {
    return new OrderEntity(createOrderReq.buyerId(), createOrderReq.datePlaced(),
        createOrderReq.totalAmount(), createOrderReq.shippingAddressId());
  }

  private List<OrdersProductsEntity> buildOrdersProducts(Long orderId,
                                                         CreateOrderReq createOrderReq) {
    return createOrderReq.items().stream()
        .map(item -> new OrdersProductsEntity(orderId, item.productId()))
        .collect(Collectors.toList());
  }

  private Order mapToOrderDomain(OrderEntity savedOrder, List<Product> orderProducts) {
    return new Order(savedOrder.getId(), orderProducts, savedOrder.getTotalAmount(),
        savedOrder.getShippingAddressId(), savedOrder.getDatePlaced());
  }

  public Optional<Order> getOrderById(Long orderId) {
    List<Long> productIds = ordersProductsRepository.findAllByOrderId(orderId).stream()
        .map(OrdersProductsEntity::getProductId).toList();
    List<Product> products = productService.getProducts(productIds);

    return orderRepository.findById(orderId).map(
        oe -> new Order(oe.getId(), products, oe.getTotalAmount(), oe.getShippingAddressId(),
            oe.getDatePlaced()));
  }
}
