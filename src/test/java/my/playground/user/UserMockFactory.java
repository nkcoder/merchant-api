package my.playground.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import my.playground.persistence.entity.UserEntity;

public class UserMockFactory {

  public static List<UserEntity> usersForReturn(int size) {
    return IntStream.range(0, size).mapToObj(i -> UserEntity.builder()
            .id((long) i).userName("name" + i).email("test@test.com")
            .password("pass" + i).dateRegistered(LocalDateTime.now()).build())
        .toList();
  }

}
