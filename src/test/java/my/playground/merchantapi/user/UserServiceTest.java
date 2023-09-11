package my.playground.merchantapi.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import my.playground.merchantapi.entity.UserEntity;
import my.playground.merchantapi.infrastructure.exception.UserNotFoundException;
import my.playground.merchantapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @InjectMocks
  private UserService userService;

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
    Optional<UserEntity> optionalUser = Optional.of(
        new UserEntity(username, "test@email.com", "testPass", "ADMIN", LocalDateTime.now()));
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

  @Test
  public void shouldReturnUpdatedUser() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(
        Optional.of(new UserEntity("name", "email", "password", "admin", LocalDateTime.now())));
    when(userRepository.save(any(UserEntity.class))).thenReturn(
        new UserEntity("newName", "newEmail@email.com", "newpassword", "admin",
            LocalDateTime.now()));

    UserUpdateReq updateReq = new UserUpdateReq(1L, "name", "email@test.com", "pwd", "USER");
    User updatedUser = userService.updateUser(userId, updateReq);

    assertNotNull(updatedUser);
    assertEquals("newName", updatedUser.userName());
    assertEquals("newEmail@email.com", updatedUser.email());
  }

  @Test
  public void shouldThrowExceptionWhenUserNotFound() {
    Long userId = 2L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    UserUpdateReq updateReq = new UserUpdateReq(1L, "name", "email@test.com", "pwd", "USER");
    assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, updateReq));
  }

}

