package ua.foxminded.universitycms.repository.user.roles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class AdminRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private AdminRepository adminRepository;

  @Test
  void findByIdShouldReturnAdminWhenIdExists() {
    String expectedId = "251ce75d-3a2e-4384-861a-2bd10569e28e";
    Optional<Admin> optionalAdmin = adminRepository.findById(
        expectedId);
    assertThat(optionalAdmin).isPresent();
    User admin = optionalAdmin.get();
    assertThat(admin.getId()).isEqualTo(expectedId);
  }

}
