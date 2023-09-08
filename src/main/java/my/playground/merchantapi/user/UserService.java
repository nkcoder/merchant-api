package my.playground.merchantapi.user;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import my.playground.merchantapi.entity.UserEntity;
import my.playground.merchantapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User register(UserRegistrationReq registrationReq) {
    UserEntity userEntity = new UserEntity(registrationReq.userName(),
        registrationReq.email(), encryptPassword(registrationReq.password()),
        registrationReq.userType(), LocalDateTime.now());
    UserEntity userSaved = userRepository.save(userEntity);
    return new User(userSaved.getUserId(), userSaved.getUserName(), userSaved.getEmail(),
        userSaved.getPassword(), userSaved.getUserType(), userSaved.getDateRegistered());
  }

  private String encryptPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
}

