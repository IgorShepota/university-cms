package ua.foxminded.universitycms.controller.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.foxminded.universitycms.service.impl.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsServiceImpl userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.
        authorizeRequests(authz -> authz
            .antMatchers("/user/edit/**", "/courses/add/**", "/courses/activate/**",
                "/courses/update/**", "/courses/deactivate/**", "/courses/delete/**")
            .hasAuthority("ROLE_ADMIN")
            .antMatchers("/", "/user/login", "/user/registration", "/students", "/teachers",
                "/courses")
            .permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(formLogin -> formLogin
            .loginPage("/user/login")
            .loginProcessingUrl("/user/perform_login")
            .defaultSuccessUrl("/", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/user/logout")
            .logoutSuccessUrl("/")
            .permitAll()
        )
        .userDetailsService(userDetailsService);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
