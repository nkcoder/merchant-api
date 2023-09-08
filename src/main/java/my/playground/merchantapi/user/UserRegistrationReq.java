package my.playground.merchantapi.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserRegistrationReq {

  @NotEmpty
  @Size(min = 3, max = 20)
  private final String userName;
  @NotEmpty
  @Email
  private final String email;
  @NotEmpty
  @Size(min = 6)
  private final String password;
  @NotEmpty
  private final String userType;
}
