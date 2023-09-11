package my.playground.merchantapi.infrastructure;

import lombok.RequiredArgsConstructor;
import my.playground.merchantapi.infrastructure.jwt.JwtAuthenticationFilter;
import my.playground.merchantapi.infrastructure.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfigurer {

  private final JwtAuthenticationFilter authenticationFilter;
  private final JwtAuthorizationFilter authorizationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize -> authorize.requestMatchers("/users/register", "/auth/login").permitAll()
                .anyRequest().authenticated())
        .addFilter(authenticationFilter)
        .addFilter(authorizationFilter);
    return http.build();
  }

}
