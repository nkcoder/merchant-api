package my.playground.merchantapi.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record UserRegistrationReq(@NotEmpty @Size(min = 3, max = 20) String userName,
                                  @NotEmpty @Email String email,
                                  @NotEmpty @Size(min = 6) String password,
                                  @NotEmpty String userType) {

}
