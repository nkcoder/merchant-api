package my.playground.persistence;

import my.playground.persistence.entity.UserEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findByUserName(String userName);

  @Modifying
  @Query("DELETE FROM users WHERE email = :email")
  void deleteByEmail(@Param("email") String email);
}

