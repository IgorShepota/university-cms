package ua.foxminded.universitycms.mapping.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.dto.user.UserResponseDTO;
import ua.foxminded.universitycms.model.entity.user.Gender;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;

class UserMapperTest {

  private final UserMapperImpl mapper = new UserMapperImpl();

  @Test
  void userToUserResponseDTOShouldMapCorrectlyIfDataIsCorrect() {
    Role role = Role.builder()
        .withName(RoleName.STUDENT)
        .build();

    User user = new User();
    user.setId(UUID.randomUUID().toString());
    user.setEmail("test@example.com");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setGender(Gender.MALE);
    user.setRole(role);

    UserResponseDTO userDTO = mapper.userToUserResponseDTO(user);

    assertThat(userDTO.getId()).isEqualTo(user.getId());
    assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
    assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstName());
    assertThat(userDTO.getLastName()).isEqualTo(user.getLastName());
    assertThat(userDTO.getGender()).isEqualTo(user.getGender());
    assertThat(userDTO.getRoleName()).isEqualTo(user.getRole().getName().name());

  }

  @Test
  void userRoleNameShouldReturnNullIfRoleNameIsNull() {
    Role role = Role.builder()
        .withName(null)
        .build();

    User user = User.builder()
        .role(role)
        .build();

    UserResponseDTO userDTO = mapper.userToUserResponseDTO(user);

    assertThat(userDTO.getRoleName()).isNull();
  }

  @Test
  void userRoleNameShouldReturnNullIfRoleIsNull() {
    User user = new User();

    UserResponseDTO userDTO = mapper.userToUserResponseDTO(user);

    assertThat(userDTO.getRoleName()).isNull();
  }

  @Test
  void userRegistrationDTOToUserShouldMapCorrectlyIfDataIsCorrect() {
    UserRegistrationDTO userRegistrationDTO = UserRegistrationDTO.builder()
        .email("test@example.com")
        .password("password")
        .firstName("John")
        .lastName("Doe")
        .gender(Gender.MALE)
        .build();

    User user = mapper.userRegistrationDTOToUser(userRegistrationDTO);

    assertThat(user.getEmail()).isEqualTo(userRegistrationDTO.getEmail());
    assertThat(user.getPassword()).isEqualTo(userRegistrationDTO.getPassword());
    assertThat(user.getFirstName()).isEqualTo(userRegistrationDTO.getFirstName());
    assertThat(user.getLastName()).isEqualTo(userRegistrationDTO.getLastName());
    assertThat(user.getGender()).isEqualTo(userRegistrationDTO.getGender());
    assertThat(user.getRole()).isNull();
  }

  @Test
  void userToUserDTOShouldReturnNullIfUserResponseIsNull() {
    UserResponseDTO userDTO = mapper.userToUserResponseDTO(null);

    assertThat(userDTO).isNull();
  }

  @Test
  void userRegistrationDTOToUserShouldReturnNullIfUserRegistrationDTOIsNull() {
    User user = mapper.userRegistrationDTOToUser(null);

    assertThat(user).isNull();
  }

}
