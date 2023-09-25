package my.playground;

import io.restassured.http.ContentType;
import my.playground.infrastructure.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.contains;

public class AuthControllerTest extends IntegrationBaseTest {

  @Test
  public void testSuccessfulLogin() {
    JwtAuthenticationFilter.LoginRequest loginRequest = new JwtAuthenticationFilter.LoginRequest(
        "test0User",
        "test0Pass!"
    );
    given()
        .contentType(ContentType.JSON)
        .body(loginRequest)
        .when()
        .post("/v1/auth/login")
        .then()
        .statusCode(HttpStatus.OK.value())
        .header("Authorization", containsString("Bearer "));
  }

  @Test
  public void testLoginWithWrongCredentials() {
    JwtAuthenticationFilter.LoginRequest loginRequest = new JwtAuthenticationFilter.LoginRequest(
        "test0User",
        "wrongPass!!"
    );
    given()
        .contentType(ContentType.JSON)
        .body(loginRequest)
        .when()
        .post("/v1/auth/login")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());
  }

  @Test
  public void testAccessWithInvalidToken() throws Exception {
    given()
        .header("Authorization", "Bearer invalidToken")
        .when()
        .get("/v1/your-secured-endpoint")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());
  }

}
