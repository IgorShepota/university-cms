package ua.foxminded.universitycms.repository.user.role;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class RoleRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private RoleRepository roleRepository;

  @Test
  void findByIdShouldReturnRoleWhenIdExists() {
    String expectedId = "6a9d5f8e-a8d8-11ed-a8fc-0242ac120002";
    Optional<Role> optionalRole = roleRepository.findById(expectedId);
    assertThat(optionalRole).isPresent();
    Role role = optionalRole.get();
    assertThat(role.getId()).isEqualTo(expectedId);
  }

  @Test
  void findByNameShouldReturnCorrectRoleWhenNameIsCorrect() {
    String expectedId = "6a9d5f8e-a8d8-11ed-a8fc-0242ac120002";

    Optional<Role> role = roleRepository.findByName(RoleName.UNVERIFIED);

    assertThat(role).isNotEmpty();
    assertThat(role.get().getId()).isEqualTo(expectedId);
  }

}
