package my.playground.merchantapi.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import my.playground.merchantapi.entity.UserEntity;
import my.playground.merchantapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

  private static UserRepository userRepository;
  private static PasswordEncoder passwordEncoder;
  private static UserService userService;

  @BeforeAll
  public static void setup() {
    passwordEncoder = mock(BCryptPasswordEncoder.class);
    userRepository = mock(UserRepository.class);
    userService = new UserService(userRepository, passwordEncoder);
  }

  @Test
  public void shouldRegisterUser() {
    // Given
    UserRegistrationReq registrationReq = new UserRegistrationReq("testUser", "test@email.com",
        "testPass", "DEFAULT_USER_TYPE");

    when(passwordEncoder.encode(registrationReq.password())).thenReturn("encoded-password");

    UserEntity mockUserEntity = new UserEntity(registrationReq.userName(), registrationReq.email(),
        passwordEncoder.encode(registrationReq.password()), registrationReq.userType(),
        LocalDateTime.now());

    when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

    // When
    User registeredUser = userService.register(registrationReq);

    // Then
    assertNotNull(registeredUser);
    assertEquals("testUser", registeredUser.userName());
    assertEquals("test@email.com", registeredUser.email());
    assertEquals("encoded-password", registeredUser.password());
    assertEquals("DEFAULT_USER_TYPE", registeredUser.userType());
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  public void shouldLoadUserByName() {
    String username = "testUser";
    Optional<UserEntity> optionalUser = Optional.of(new UserEntity(
        username, "test@email.com", "testPass", "ADMIN", LocalDateTime.now()
    ));
    when(userRepository.findByUserName(username)).thenReturn(optionalUser);

    UserDetails userDetails = userService.loadUserByUsername(username);
    assertNotNull(userDetails);
    assertEquals(username, userDetails.getUsername());
  }

  @Test
  public void shouldThrowExceptionWhenLoadUserByNameAndNameNotExist() {
    String username = "testUser";
    Optional<UserEntity> optionalUser = Optional.empty();
    when(userRepository.findByUserName(username)).thenReturn(optionalUser);

    assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
  }

}

