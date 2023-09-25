package my.playground.user;

import java.io.Serializable;
import java.time.LocalDateTime;

public record User(Long userId, String userName, String email, String password,
                   LocalDateTime registeredDate) implements Serializable {

  public User shadowPassword() {
    return new User(this.userId(), this.userName(), this.email(), null,
        this.registeredDate());
  }
}
