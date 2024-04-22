package ua.foxminded.universitycms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.Group;

class GroupRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private GroupRepository groupRepository;

  @Test
  void findByIdShouldReturnGroupWhenIdExists() {
    String expectedId = "7a9d5f8e-a8d8-11ed-a8fc-0242ac120002";
    Optional<Group> optionalGroup = groupRepository.findById(
        expectedId);
    assertThat(optionalGroup).isPresent();
    Group group = optionalGroup.get();
    assertThat(group.getId()).isEqualTo(expectedId);
  }

}
