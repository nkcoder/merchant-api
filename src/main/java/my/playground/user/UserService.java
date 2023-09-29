package my.playground.user;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import my.playground.infrastructure.exception.AppException;
import my.playground.persistence.UserRepository;
import my.playground.persistence.entity.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User register(UserRegistrationReq registrationReq) {
    UserEntity userEntity = UserEntity.builder().userName(registrationReq.userName())
        .email(registrationReq.email()).password(passwordEncoder.encode(registrationReq.password()))
        .dateRegistered(LocalDateTime.now()).build();
    UserEntity userSaved = userRepository.save(userEntity);
    return new User(userSaved.getId(), userSaved.getUserName(), userSaved.getEmail(),
        userSaved.getPassword(), userSaved.getDateRegistered());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUserName(username).map(
            userEntity -> new org.springframework.security.core.userdetails.User(
                userEntity.getUserName(), userEntity.getPassword(), List.of()))
        .orElseThrow(() -> new UsernameNotFoundException("user not exist: " + username));
  }

  public User updateUser(Long userId, UserUpdateReq updateReq) {
    UserEntity newEntity = userRepository.findById(userId).map(
            existingUser -> UserEntity.builder().id(userId).userName(updateReq.userName())
                .email(updateReq.email()).password(updateReq.password())
                .dateRegistered(existingUser.getDateRegistered()).build())
        .orElseThrow(() -> AppException.from(HttpStatus.NOT_FOUND, "User not found: " + userId));

    UserEntity updatedEntity = userRepository.save(newEntity);
    return new User(userId, updatedEntity.getUserName(), updatedEntity.getEmail(), null,
        updatedEntity.getDateRegistered());
  }

  public List<User> getUsers(int pageNumber, int pageSize) {
    return userRepository.findAll(
            PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "dateRegistered"))).stream()
        .map(userEntity -> new User(userEntity.getId(), userEntity.getUserName(),
            userEntity.getEmail(), null, userEntity.getDateRegistered())).toList();
  }

}

