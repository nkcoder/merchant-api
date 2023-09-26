package my.playground.user;

import my.playground.infrastructure.exception.AppException;
import my.playground.persistence.UserRepository;
import my.playground.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        "testPass");

    when(passwordEncoder.encode(registrationReq.password())).thenReturn("encoded-password");

    UserEntity mockUserEntity = new UserEntity(registrationReq.userName(), registrationReq.email(),
        passwordEncoder.encode(registrationReq.password()),
        LocalDateTime.now());

    when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

    // When
    User registeredUser = userService.register(registrationReq);

    // Then
    assertNotNull(registeredUser);
    assertEquals("testUser", registeredUser.userName());
    assertEquals("test@email.com", registeredUser.email());
    assertEquals("encoded-password", registeredUser.password());
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  public void shouldLoadUserByName() {
    String username = "testUser";
    Optional<UserEntity> optionalUser = Optional.of(
        new UserEntity(username, "test@email.com", "testPass", LocalDateTime.now()));
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
        Optional.of(new UserEntity("name", "email", "password", LocalDateTime.now())));
    when(userRepository.save(any(UserEntity.class))).thenReturn(
        new UserEntity("newName", "newEmail@email.com", "newpassword",
            LocalDateTime.now()));

    UserUpdateReq updateReq = new UserUpdateReq(1L, "name", "email@test.com", "pwd");
    User updatedUser = userService.updateUser(userId, updateReq);

    assertNotNull(updatedUser);
    assertEquals("newName", updatedUser.userName());
    assertEquals("newEmail@email.com", updatedUser.email());
  }

  @Test
  public void shouldThrowExceptionWhenUserNotFound() {
    Long userId = 2L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    UserUpdateReq updateReq = new UserUpdateReq(1L, "name", "email@test.com", "pwd");
    assertThrows(AppException.class, () -> userService.updateUser(userId, updateReq));
  }

}

