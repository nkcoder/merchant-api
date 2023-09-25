package my.playground.user;

import io.restassured.http.ContentType;
import my.playground.IntegrationBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserControllerTest extends IntegrationBaseTest {

  @Test
  public void shouldRegisterUser() {
    given().body(new UserRegistrationReq("test1User", "test1@email.com", "test1Pass!"))
        .contentType(ContentType.JSON).when().post("/users/register").then()
        .statusCode(HttpStatus.CREATED.value()).body("userName", equalTo("test1User"))
        .body("email", equalTo("test1@email.com"));
  }

  @Test
  public void shouldReturnBadRequestWhenUserEmailIsInvalid() {
    given().body(new UserRegistrationReq("testUser", "test.email", "testPass!"))
        .contentType(ContentType.JSON).when().post("/users/register").then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void shouldReturnBadRequestWhenUserNameIsTooShort() {
    given().body(new UserRegistrationReq("sa", "test.email", "testPass!"))
        .contentType(ContentType.JSON).when().post("/users/register").then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void shouldReturnBadRequestWhenPasswordIsTooShort() {
    given().body(new UserRegistrationReq("testUser", "test@email.com", "test!"))
        .contentType(ContentType.JSON).when().post("/users/register").then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void shouldReturnUpdatedUser() {
    given().header("Authorization", generateJwtToken())
        .body(new UserUpdateReq(userId, "newName", "newEmail@test.com", "newPassword!"))
        .contentType(ContentType.JSON).when().put("/users/" + userId).then()
        .statusCode(HttpStatus.OK.value()).body("userName", equalTo("newName"));
  }

  @Test
  public void shouldFailToUpdateWhenInvalidEmail() {
    given().header("Authorization", generateJwtToken())
        .body(new UserUpdateReq(userId, "newName", "newEmail", "newPassword!"))
        .contentType(ContentType.JSON).when().put("/users/" + userId).then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void shouldFailToUpdateWhenPasswordIsTooShort() {
    given().header("Authorization", generateJwtToken())
        .body(new UserUpdateReq(userId, "newName", "newEmail@test.com", "new!"))
        .contentType(ContentType.JSON).when().put("/users/" + userId).then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

}
