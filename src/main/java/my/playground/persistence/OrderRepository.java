package my.playground.persistence;

import my.playground.persistence.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long>,
    CrudRepository<OrderEntity, Long> {

  Page<OrderEntity> findAllByBuyerId(Long buyerId, Pageable pageable);

}
