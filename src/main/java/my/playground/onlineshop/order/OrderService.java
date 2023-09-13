package my.playground.onlineshop.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import my.playground.onlineshop.persistence.OrderRepository;
import my.playground.onlineshop.persistence.PaymentRepository;
import my.playground.onlineshop.persistence.entity.OrderEntity;
import my.playground.onlineshop.persistence.entity.PaymentEntity;
import my.playground.onlineshop.product.Product;
import my.playground.onlineshop.product.ProductNotFoundException;
import my.playground.onlineshop.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;
  private final ProductService productService;

  @Transactional
  public Order createOrder(CreateOrderReq createOrderReq) {
    List<Product> orderProducts = updateProductStocks(createOrderReq.items());
    Payment payment = savePayment(createOrderReq.payment());

    OrderEntity orderEntity = buildOrderEntityFromRequest(createOrderReq, payment);
    OrderEntity savedOrder = orderRepository.save(orderEntity);

    return mapToOrderDomain(savedOrder, orderProducts, createOrderReq.payment());
  }

  private List<Product> updateProductStocks(List<OrderItem> orderItems) {
    List<Product> orderProducts = new ArrayList<>();
    for (OrderItem item : orderItems) {
      Product product = productService.getProductById(item.productId())
          .orElseThrow(() -> new ProductNotFoundException("productId: " + item.productId()));
      if (product.stockQuantity() < item.quantity()) {
        throw new OutOfStockException("productId: " + item.productId());
      }
      Product updatedProduct = productService.updateProduct(item.productId(),
          product.withStockUpdated(product.stockQuantity() - item.quantity()));
      orderProducts.add(updatedProduct);
    }
    return orderProducts;
  }

  private Payment savePayment(Payment payment) {
    PaymentEntity paymentEntity = new PaymentEntity(payment.amount(), payment.paymentMethod(),
        payment.datePaid(), payment.paymentStatus());
    PaymentEntity savedPayment = paymentRepository.save(paymentEntity);
    return new Payment(savedPayment.getPaymentId(), payment.amount(), payment.paymentMethod(),
        payment.datePaid(), payment.paymentStatus());
  }

  private OrderEntity buildOrderEntityFromRequest(CreateOrderReq createOrderReq, Payment payment) {
    return new OrderEntity(
        createOrderReq.buyerId(),
        createOrderReq.datePlaced(),
        payment.amount(),
        createOrderReq.shippingAddressId(),
        createOrderReq.billingAddressId(),
        payment.id()
    );
  }

  private Order mapToOrderDomain(OrderEntity savedOrder, List<Product> orderProducts,
      Payment payment) {
    return new Order(
        savedOrder.getOrderId(),
        orderProducts,
        payment,
        savedOrder.getTotalAmount(),
        savedOrder.getShippingAddressId(),
        savedOrder.getBillingAddressId(),
        savedOrder.getDatePlaced()
    );
  }

  public Optional<Order> getOrderById(Long orderId) {
    return orderRepository.findById(orderId).flatMap(oe ->
        paymentRepository.findById(oe.getPaymentId()).map(pe ->
            new Order(
                oe.getOrderId(),
                List.of(),
                new Payment(pe.getPaymentId(), pe.getAmount(), pe.getPaymentMethod(),
                    pe.getDatePaid(),
                    pe.getPaymentStatus()),
                oe.getTotalAmount(),
                oe.getShippingAddressId(),
                oe.getBillingAddressId(),
                oe.getDatePlaced()
            )
        )
    );
  }
}
