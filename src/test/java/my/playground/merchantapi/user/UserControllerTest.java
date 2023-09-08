package my.playground.merchantapi.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  public void shouldRegisterUser() throws Exception {
    User user = new User(1L, "testUser", "test@email.com", null, "Admin",
        LocalDateTime.now());

    when(userService.register(any(UserRegistrationReq.class))).thenReturn(user);

    mockMvc.perform(post("/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"userName": "testUser", "email": "test@email.com", "password": "testPass", "userType": "Admin"}
                """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.userId").value(1L))
        .andExpect(jsonPath("$.userName").value("testUser"))
        .andExpect(jsonPath("$.email").value("test@email.com"));
  }

  @Test
  public void shouldReturnBadRequestWhenUserEmailIsInvalid() throws Exception {
    mockMvc.perform(post("/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"userName": "testUser", "email": "test.email", "password": "testPass", "userType": "Admin"}
                """))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenUserNameIsTooShort() throws Exception {
    mockMvc.perform(post("/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"userName": "sa", "email": "test@email.com", "password": "testPass", "userType": "Admin"}
                """))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestWhenPasswordIsTooShort() throws Exception {
    mockMvc.perform(post("/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"userName": "testUser", "email": "test@email.com", "password": "test", "userType": "Admin"}
                """))
        .andExpect(status().isBadRequest());
  }
}





