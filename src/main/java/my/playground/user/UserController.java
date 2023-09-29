package my.playground.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(
      @RequestBody @Valid UserRegistrationReq registrationReq) {
    logger.info("Received register request.");
    User registeredUser = userService.register(registrationReq);
    return new ResponseEntity<>(registeredUser.shadowPassword(), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id,
      @RequestBody @Valid UserUpdateReq updateReq) {
    User updatedUser = userService.updateUser(id, updateReq);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<User>> getUsers(
      @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    return new ResponseEntity<>(userService.getUsers(pageNumber, pageSize), HttpStatus.OK);
  }

}
