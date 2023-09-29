package my.playground.persistence;

import java.util.List;
import my.playground.persistence.entity.OrdersProductsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersProductsRepository extends CrudRepository<OrdersProductsEntity, Long> {

  List<OrdersProductsEntity> findAllByOrderId(Long orderId);

}
