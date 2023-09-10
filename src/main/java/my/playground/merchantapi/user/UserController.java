package my.playground.merchantapi.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
    User updatedUser = userService.updateUser(id, user);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }
}
