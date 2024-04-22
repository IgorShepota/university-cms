package ua.foxminded.universitycms.repository.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class UserRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void findByIdShouldReturnUserWhenIdExists() {
    String expectedId = "1a9d5f8e-a8d8-11ed-a8fc-0242ac120002";
    Optional<User> optionalUser = userRepository.findById(expectedId);
    assertThat(optionalUser).isPresent();
    User user = optionalUser.get();
    assertThat(user.getId()).isEqualTo(expectedId);
  }

  @Test
  void findAllByRoleNameShouldReturnListOfUsersWhenDataCorrect() {
    List<User> studentUsers = userRepository.findAllByRoleName(RoleName.STUDENT);

    assertThat(studentUsers).isNotEmpty();
    assertThat(studentUsers.stream()
        .map(User::getRole)
        .map(Role::getName)
        .allMatch(RoleName.STUDENT::equals)).isTrue();
  }

}
