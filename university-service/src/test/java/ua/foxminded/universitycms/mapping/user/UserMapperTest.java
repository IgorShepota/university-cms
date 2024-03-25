package ua.foxminded.universitycms.mapping.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.model.entity.user.User;

class UserMapperTest {

  private final UserMapper mapper = new UserMapperImpl();

  @Test
  void userToUserDTOShouldMapCorrectlyIfDataIsCorrect() {
    User user = new User();
    user.setId(UUID.randomUUID().toString());
    user.setEmail("test@example.com");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setGender("Male");

    UserDTO userDTO = mapper.userToUserDTO(user);

    assertThat(userDTO.getId()).isEqualTo(user.getId());
    assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
    assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstName());
    assertThat(userDTO.getLastName()).isEqualTo(user.getLastName());
    assertThat(userDTO.getGender()).isEqualTo(user.getGender());
  }

  @Test
  void userDTOToUserShouldReturnNotNullWhenDataIsCorrect() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(UUID.randomUUID().toString());
    userDTO.setEmail("example@test.com");
    userDTO.setFirstName("Jane");
    userDTO.setLastName("Doe");
    userDTO.setGender("Female");

    User user = mapper.userDTOToUser(userDTO);

    assertThat(user).isNotNull();
  }

  @Test
  void userToUserDTOShouldReturnNullWhenUserIsNull() {
    assertThat(mapper.userToUserDTO(null)).isNull();
  }

  @Test
  void userDTOToUserShouldReturnNullWhenUserDTOIsNull() {
    assertThat(mapper.userDTOToUser(null)).isNull();
  }

}
