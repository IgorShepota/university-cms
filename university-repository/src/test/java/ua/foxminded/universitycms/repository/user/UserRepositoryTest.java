package ua.foxminded.universitycms.repository.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class UserRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void findByIdShouldReturnUserWhenIdExists() {
    String expectedId = "251ce75d-3a2e-4384-861a-2bd10569e28e";
    Optional<User> optionalUser = userRepository.findById(expectedId);
    assertThat(optionalUser).isPresent();
    User user = optionalUser.get();
    assertThat(user.getId()).isEqualTo(expectedId);
  }

}
