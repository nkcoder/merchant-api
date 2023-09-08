package my.playground.merchantapi.infrastructure;

import my.playground.merchantapi.infrastructure.exception.InvalidArgumentException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.StringUtils;

public class PasswordEncryption {

  public static String encrypt(String password) {
    if (!StringUtils.hasText(password)) {
      throw new InvalidArgumentException();
    }
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public static boolean check(String password, String encrypted) {
    return BCrypt.checkpw(password, encrypted);
  }

}
