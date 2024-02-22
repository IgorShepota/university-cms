package ua.foxminded.universitycms.service.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.entity.group.Group;
import ua.foxminded.universitycms.repository.group.GroupRepository;

@SpringBootTest
class GroupServiceTest {

  @MockBean
  private GroupRepository groupRepository;

  @Autowired
  private GroupService groupService;

  @Test
  void addGroupShouldWorkCorrectlyIfGroupEntityCorrect() {
    Group newGroup = Group.builder().withId("1").withName("FLA-001").build();

    groupService.addGroup(newGroup);

    verify(groupRepository).save(newGroup);
  }

  @Test
  void getGroupByIdShouldReturnCorrectGroupIfExists() {
    String groupId = "1";
    Group mockGroup = Group.builder().withId(groupId).withName("FLA-001").build();

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(mockGroup));

    Optional<Group> result = groupService.getGroupById(groupId);

    assertThat(result).isPresent();
    assertThat(mockGroup).isEqualTo(result.get());
  }

  @Test
  void getGroupByIdShouldReturnEmptyIfGroupDoesNotExist() {
    String nonExistentId = "nonexistent";
    when(groupRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    Optional<Group> result = groupService.getGroupById(nonExistentId);

    assertThat(result).isEmpty();
  }

  @Test
  void getAllGroupsShouldReturnAllGroups() {
    List<Group> mockGroups = Arrays.asList(Group.builder().withId("1").withName("FLA-001").build(),
        Group.builder().withId("2").withName("FLA-002").build());

    when(groupRepository.findAll()).thenReturn(mockGroups);

    List<Group> groups = groupService.getAllGroups();

    assertThat(groups).hasSize(2).isEqualTo(mockGroups);
  }

  @Test
  void getAllGroupsWithPaginationShouldReturnCorrectData() {
    Group group1 = Group.builder().withId("1").withName("FLA-001").build();
    Group group2 = Group.builder().withId("2").withName("FLA-002").build();

    List<Group> groups = Arrays.asList(group1, group2);
    Page<Group> groupPage = new PageImpl<>(groups);

    when(groupRepository.findAll(any(Pageable.class))).thenReturn(groupPage);

    List<Group> returnedGroups = groupService.getAllGroups(1, 2);

    assertThat(returnedGroups).hasSize(2).containsExactlyInAnyOrder(group1, group2);

    verify(groupRepository).findAll(PageRequest.of(0, 2));
  }

  @Test
  void updateGroupShouldCallSaveMethod() {
    Group groupToUpdate = Group.builder().withId("1").withName("FLA-001").build();

    groupService.updateGroup(groupToUpdate);

    verify(groupRepository).save(groupToUpdate);
  }

  @Test
  void deleteGroupShouldWorkCorrectlyIfGroupExists() {
    String groupId = "1";

    when(groupRepository.existsById(groupId)).thenReturn(true);

    boolean result = groupService.deleteGroup(groupId);

    assertThat(result).isTrue();
    verify(groupRepository).deleteById(groupId);
  }

  @Test
  void deleteGroupShouldReturnFalseIfGroupNotExists() {
    String groupId = "nonexistent";

    when(groupRepository.existsById(groupId)).thenReturn(false);

    boolean result = groupService.deleteGroup(groupId);

    assertThat(result).isFalse();
  }
}
