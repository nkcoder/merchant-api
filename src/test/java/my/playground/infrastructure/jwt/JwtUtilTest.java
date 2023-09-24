package my.playground.infrastructure.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jsonwebtoken.security.SignatureException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JwtUtilTest {

  @Autowired
  private JwtUtil jwtUtil;

  private UserDetails userDetails;

  @BeforeEach
  public void setUp() {
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