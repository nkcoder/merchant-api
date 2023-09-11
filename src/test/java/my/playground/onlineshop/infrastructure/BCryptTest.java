package my.playground.onlineshop.infrastructure;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  public void shouldEncryptAndVerify() {
    String password = "susan.pwd";
    String encoded = passwordEncoder.encode(password);
    assertTrue(passwordEncoder.matches(password, encoded));
  }

  @Test
  public void shouldFailToVerifyCorruptedPassword() {
    String password = "susan.pwd";
    String encoded = passwordEncoder.encode(password);
    String corruptedPassword = password + "-*&*%";
    assertFalse(passwordEncoder.matches(corruptedPassword, encoded));
  }

}
