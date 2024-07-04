package ua.foxminded.universitycms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.GroupStatus;

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

  @Test
  void existsByNameIgnoreCaseShouldReturnTrueWhenGroupExists() {
    boolean exists = groupRepository.existsByNameIgnoreCase("fla-101");
    assertThat(exists).isTrue();
  }

  @Test
  void existsByNameIgnoreCaseShouldReturnFalseWhenGroupDoesNotExist() {
    boolean exists = groupRepository.existsByNameIgnoreCase("Non-existent Group");
    assertThat(exists).isFalse();
  }

  @Test
  void findAllByStatusWithStudentsShouldReturnGroupsWithGivenStatus() {
    List<Group> activeGroups = groupRepository.findAllByStatusWithStudents(GroupStatus.ACTIVE);

    assertThat(activeGroups).hasSize(3);
    assertThat(activeGroups.get(0).getName()).isEqualTo("FLA-101");
    assertThat(activeGroups.get(0).getStatus()).isEqualTo(GroupStatus.ACTIVE);
  }

  @Test
  void findAllByStatusWithStudents_shouldReturnEmptyList_whenNoGroupsWithGivenStatus() {
    List<Group> deletedGroups = groupRepository.findAllByStatusWithStudents(GroupStatus.DELETED);

    assertThat(deletedGroups).isEmpty();
  }

  @Test
  void findAllByStatusWithStudentsShouldFetchStudents() {
    List<Group> activeGroups = groupRepository.findAllByStatusWithStudents(GroupStatus.ACTIVE);

    assertThat(activeGroups).hasSize(3);
    assertThat(activeGroups.get(0).getStudents()).hasSize(1);
    assertThat(
        activeGroups.get(0).getStudents().iterator().next().getUser().getFirstName()).isEqualTo(
        "John");
  }

}
