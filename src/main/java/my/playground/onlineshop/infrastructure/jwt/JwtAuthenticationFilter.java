package my.playground.onlineshop.infrastructure.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import my.playground.onlineshop.infrastructure.JsonUtil;
import my.playground.onlineshop.infrastructure.exception.InvalidCredentialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private final JwtUtil jwtUtil;
  private final JsonUtil jsonUtil;

  @Autowired
  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
      JsonUtil jsonUtil) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
    this.jsonUtil = jsonUtil;
    setFilterProcessesUrl("/auth/login");
  }

  /*
  The UsernamePasswordAuthenticationFilter by default attempts to retrieve the username and
  password from request parameters. This is generally suitable for traditional form-based
  login mechanisms.
     return super.attemptAuthentication(request, response);
 */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    logger.info("processing request: {}", request.getRequestURL());
    try {
      LoginRequest loginRequest = jsonUtil.fromJson(request.getInputStream(), LoginRequest.class);
      // Create the auth token using the parsed data
      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
          loginRequest.username(), loginRequest.password(), Collections.emptyList());

      // Allow subclasses to set the "details" property
      setDetails(request, authRequest);
      return this.getAuthenticationManager().authenticate(authRequest);
    } catch (IOException e) {
      logger.error("Authenticate request error: {}", e.getMessage());
      throw new InvalidCredentialException("Invalid token");
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) {
    UserDetails userDetails = (UserDetails) authResult.getPrincipal();
    String token = jwtUtil.generateToken(userDetails);
    response.addHeader("Authorization", "Bearer " + token);
  }

  public record LoginRequest(String username, String password) {

  }

}


