package my.playground.merchantapi.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(
      @RequestBody @Valid UserRegistrationReq registrationReq) {
    User registeredUser = userService.register(registrationReq);
    return new ResponseEntity<>(registeredUser.shadowPassword(), HttpStatus.CREATED);
  }
}
