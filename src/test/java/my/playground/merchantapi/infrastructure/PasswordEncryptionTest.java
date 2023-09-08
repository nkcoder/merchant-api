package my.playground.merchantapi.infrastructure;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import my.playground.merchantapi.infrastructure.exception.InvalidArgumentException;
import org.junit.jupiter.api.Test;

public class PasswordEncryptionTest {

  @Test
  public void shouldEncryptPassword() {
    String password = "my-password!";
    String encrypted = PasswordEncryption.encrypt(password);
    assertTrue(PasswordEncryption.check(password, encrypted));
  }

  @Test
  public void shouldThrowExceptionWhenPasswordIsInvalid() {
    String password = "    ";
    assertThrows(InvalidArgumentException.class, () -> PasswordEncryption.encrypt(password));
  }

}
