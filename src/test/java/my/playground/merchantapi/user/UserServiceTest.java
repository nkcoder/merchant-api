package my.playground.merchantapi.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import my.playground.merchantapi.entity.UserEntity;
import my.playground.merchantapi.infrastructure.PasswordEncryption;
import my.playground.merchantapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @Test
  public void testRegister() {
    // Given
    UserRegistrationReq registrationReq = new UserRegistrationReq("testUser", "test@email.com",
        "testPass", "DEFAULT_USER_TYPE");

    UserEntity mockUserEntity = new UserEntity(registrationReq.userName(), registrationReq.email(),
        PasswordEncryption.encrypt(registrationReq.password()), registrationReq.userType(),
        LocalDateTime.now());

    when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

    // When
    User registeredUser = userService.register(registrationReq);

    // Then
    assertNotNull(registeredUser);
    assertEquals("testUser", registeredUser.userName());
    assertEquals("test@email.com", registeredUser.email());
    assertTrue(PasswordEncryption.check(registrationReq.password(), registeredUser.password()));
    assertEquals("DEFAULT_USER_TYPE", registeredUser.userType());
  }

}

