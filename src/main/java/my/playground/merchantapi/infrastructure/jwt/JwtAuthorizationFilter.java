package my.playground.merchantapi.infrastructure.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
      UserDetailsService userDetailsService) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      logger.error("No authorization info in the request header.");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    try {
      Authentication authentication = getAuthentication(request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception exception) {
      logger.error("Authentication failed: {}", exception.getMessage());
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    String username = jwtUtil.extractUsername(token.substring(7));
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (jwtUtil.validateToken(token.substring(7), userDetails)) {
      return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }
    return null;
  }

}
