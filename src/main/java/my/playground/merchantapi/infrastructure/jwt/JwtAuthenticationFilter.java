package my.playground.merchantapi.infrastructure.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    UserDetails userDetails = (UserDetails) authResult.getPrincipal();
    String jwt = jwtUtil.generateToken(userDetails);
    response.addHeader("Authorization", "Bearer " + jwt);
  }
}
