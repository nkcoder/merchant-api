package my.playground.merchantapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import my.playground.merchantapi.infrastructure.exception.GlobalExceptionHandler;
import my.playground.merchantapi.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private AuthenticationManager authenticationManager;

  @Test
  public void testSuccessfulLogin() throws Exception {
    User user = new User("testUsername", "testPassword", List.of());
    when(userService.loadUserByUsername(any())).thenReturn(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(user, "testPassword");
    when(authenticationManager.authenticate(any())).thenReturn(authentication);

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"testUsername\",\"password\":\"testPassword\"}"))
        .andExpect(status().isOk())
        .andExpect(header().exists("Authorization")); // Check JWT token presence
  }

  @Test
  public void testLoginWithWrongCredentials() throws Exception {
    when(userService.loadUserByUsername(any())).thenThrow(
        new UsernameNotFoundException("User not found"));

    when(authenticationManager.authenticate(any())).thenThrow(UsernameNotFoundException.class);

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"wrongUsername\",\"password\":\"wrongPassword\"}"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void testAccessWithInvalidToken() throws Exception {
    mockMvc.perform(get("/your-secured-endpoint")
            .header("Authorization", "Bearer invalidToken"))
        .andExpect(status().isUnauthorized());
  }


}
