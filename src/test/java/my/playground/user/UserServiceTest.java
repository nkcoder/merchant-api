package my.playground.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import my.playground.infrastructure.exception.AppException;
import my.playground.persistence.UserRepository;
import my.playground.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
  public void registerUser_shouldRegister() {
    // Given
    UserRegistrationReq registrationReq = new UserRegistrationReq("testUser", "test@email.com",
        "testPass");

    when(passwordEncoder.encode(registrationReq.password())).thenReturn("encoded-password");

    UserEntity mockUserEntity = UserEntity.builder().userName(registrationReq.userName())
        .email(registrationReq.email()).password(passwordEncoder.encode(registrationReq.password()))
        .dateRegistered(LocalDateTime.now()).build();

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
        UserEntity.builder().userName(username).email("test@email.com").password("encodedPass")
            .dateRegistered(LocalDateTime.now()).build());
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
    when(userRepository.findById(userId)).thenReturn(Optional.of(
        UserEntity.builder().userName("name").email("test@email.com").password("encodedPass")
            .dateRegistered(LocalDateTime.now()).build()));
    when(userRepository.save(any(UserEntity.class))).thenReturn(
        UserEntity.builder().userName("newName").email("newEmail@email.com")
            .password("newEncodedPass")
            .dateRegistered(LocalDateTime.now()).build());

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

  @Test
  public void getUsers_shouldReturnEmptyWhenNoUsers() {
    when(userRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());

    List<User> users = userService.getUsers(2, 10);
    assertTrue(users.isEmpty());
  }

  @Test
  public void getUsers_shouldReturnUsers() {
    when(userRepository.findAll(any(PageRequest.class))).thenReturn(
        new PageImpl<>(UserMockFactory.usersForReturn(3))
    );

    List<User> users = userService.getUsers(1, 10);
    assertEquals(3, users.size());
  }

}

