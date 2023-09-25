package my.playground.persistence;

import my.playground.persistence.entity.OrdersProductsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersProductsRepository extends CrudRepository<OrdersProductsEntity, Long> {

  List<OrdersProductsEntity> findAllByOrderId(Long orderId);

}
