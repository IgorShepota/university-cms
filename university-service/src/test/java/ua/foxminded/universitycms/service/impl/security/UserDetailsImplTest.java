package ua.foxminded.universitycms.service.impl.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.foxminded.universitycms.model.entity.user.Gender;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;

class UserDetailsImplTest {

  @Test
  void testUserDetailsInitializationShouldBeCorrectWithCorrectData() {
    Role role = new Role();
    role.setName(RoleName.ADMIN);

    User user = User.builder()
        .email("test@example.com")
        .password("securepassword")
        .firstName("John")
        .lastName("Doe")
        .gender(Gender.MALE)
        .role(role)
        .build();

    UserDetails userDetails = new UserDetailsImpl(user);

    assertThat(userDetails.getUsername()).isEqualTo("test@example.com");
    assertThat(userDetails.getPassword()).isEqualTo("securepassword");
    assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    assertThat(userDetails.isAccountNonExpired()).isTrue();
    assertThat(userDetails.isAccountNonLocked()).isTrue();
    assertThat(userDetails.isCredentialsNonExpired()).isTrue();
    assertThat(userDetails.isEnabled()).isTrue();
  }

}
