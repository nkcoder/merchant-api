package my.playground.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.http.ContentType;
import my.playground.IntegrationBaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class UserControllerTest extends IntegrationBaseTest {

  private static final String TEST_EMAIL = "test1@email.com";

  @AfterEach
  public void teardown() {
    userRepository.deleteById(userId);
    userRepository.deleteByEmail(TEST_EMAIL);
  }

  @Test
  public void shouldRegisterUser() {
    given().body(new UserRegistrationReq("test1User", TEST_EMAIL, "test1Pass!"))
        .contentType(ContentType.JSON).when().post("/v1/users/register").then()
        .statusCode(HttpStatus.CREATED.value()).body("userName", equalTo("test1User"))
        .body("email", equalTo("test1@email.com"));
  }

  @Test
  public void shouldReturnBadRequestWhenUserEmailIsInvalid() {
    given().body(new UserRegistrationReq("testUser", "test.email", "testPass!"))
        .contentType(ContentType.JSON).when().post("/v1/users/register").then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void shouldReturnBadRequestWhenUserNameIsTooShort() {
    given().body(new UserRegistrationReq("sa", "test.email", "testPass!"))
        .contentType(ContentType.JSON).when().post("/v1/users/register").then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void shouldReturnBadRequestWhenPasswordIsTooShort() {
    given().body(new UserRegistrationReq("testUser", "test@email.com", "test!"))
        .contentType(ContentType.JSON).when().post("/v1/users/register").then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void shouldReturnUpdatedUser() {
    given().header("Authorization", generateJwtToken())
        .body(new UserUpdateReq(userId, "newName", "newEmail@test.com", "newPassword!"))
        .contentType(ContentType.JSON).when().put("/v1/users/" + userId).then()
        .statusCode(HttpStatus.OK.value()).body("userName", equalTo("newName"));
  }

  @Test
  public void shouldFailToUpdateWhenInvalidEmail() {
    given().header("Authorization", generateJwtToken())
        .body(new UserUpdateReq(userId, "newName", "newEmail", "newPassword!"))
        .contentType(ContentType.JSON).when().put("/v1/users/" + userId).then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void shouldFailToUpdateWhenPasswordIsTooShort() {
    given().header("Authorization", generateJwtToken())
        .body(new UserUpdateReq(userId, "newName", "newEmail@test.com", "new!"))
        .contentType(ContentType.JSON).when().put("/v1/users/" + userId).then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void shouldReturnAllUsers() {
    given().header("Authorization", generateJwtToken())
        .when().get("/v1/users?pageNumber=1&pageSize=10").then()
        .statusCode(HttpStatus.OK.value());
  }

}
