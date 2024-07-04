package ua.foxminded.universitycms.service.impl.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.foxminded.universitycms.model.entity.user.Gender;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;
import ua.foxminded.universitycms.repository.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserDetailsServiceImpl userDetailsService;

  @Test
  void loadUserByUsernameShouldReturnsUserDetailsWhenUserExists() {
    String email = "user@example.com";

    Role role = new Role();
    role.setName(RoleName.ADMIN);

    User user = User.builder()
        .email(email)
        .password("securepassword")
        .firstName("John")
        .lastName("Doe")
        .gender(Gender.MALE)
        .role(role)
        .build();

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

    assertThat(userDetails.getUsername()).isEqualTo(email);
    verify(userRepository).findByEmail(email);
  }

  @Test
  void loadUserByUsernameShouldThrowsUsernameNotFoundExceptionWhenUserDoesNotExist() {
    String email = "nonexistent@example.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userDetailsService.loadUserByUsername(email))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessageContaining("User not found with email: " + email);
  }

}
