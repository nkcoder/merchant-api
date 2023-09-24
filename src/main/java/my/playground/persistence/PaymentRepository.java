package my.playground.persistence;

import my.playground.persistence.entity.PaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {

}
