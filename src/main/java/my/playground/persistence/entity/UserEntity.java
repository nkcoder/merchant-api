package my.playground.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("users")
@Data
@Builder
public class UserEntity {
  @Id
  private Long id;
  private String userName;
  private String email;
  private String password;
  private LocalDateTime dateRegistered;
}
