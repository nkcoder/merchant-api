package my.playground.merchantapi.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateReq(@NotNull Long userId,
                            @NotEmpty @Size(min = 3, max = 20) String userName,
                            @NotEmpty @Email String email,
                            @NotEmpty @Size(min = 6) String password,
                            @NotEmpty String userType) {

}
