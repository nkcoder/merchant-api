package my.playground.infrastructure.jwt;

import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class JwtUtilTest {

  private final JwtUtil jwtUtil = new JwtUtil("bXlKV1Qtc2VjcmV0LWtleS0yMDIzLUFVLTAwOTEwLVh", 3600);

  private static UserDetails userDetails;

  @BeforeAll
  public static void beforeEach() {
    userDetails = new org.springframework.security.core.userdetails.User(
        "testUser",
        "testPass",
        List.of()
    );
  }

  @Test
  public void shouldGenerateTokenAndValidateSuccessfully() {
    // Generate token for user
    String token = jwtUtil.generateToken(userDetails);

    // Token should not be null or empty
    assertNotNull(token);
    assertFalse(token.isEmpty());

    // Validate the token
    assertTrue(jwtUtil.validateToken(token, userDetails));

    // Extract username from token
    String extractedUsername = jwtUtil.extractUsername(token);
    assertEquals(userDetails.getUsername(), extractedUsername);
  }

  @Test
  public void shouldFailValidationForModifiedToken() {
    // Generate token for user
    String token = jwtUtil.generateToken(userDetails) + "randomString";

    // Token should not be valid
    assertThrows(SignatureException.class, () -> jwtUtil.validateToken(token, userDetails));
  }
}