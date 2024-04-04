package ua.foxminded.universitycms.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.mapping.GroupMapper;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.repository.GroupRepository;
import ua.foxminded.universitycms.service.impl.GroupServiceImpl;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

  @Mock
  private GroupRepository groupRepository;

  @Mock
  private GroupMapper groupMapper;

  @InjectMocks
  private GroupServiceImpl groupService;

  @Test
  void addGroupShouldWorkCorrectlyIfGroupEntityCorrect() {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setId("test-id");
    Group group = new Group();
    group.setId(groupDTO.getId());

    when(groupMapper.groupDTOToGroup(any(GroupDTO.class))).thenReturn(group);
    when(groupRepository.save(any(Group.class))).thenReturn(group);

    groupService.addGroup(groupDTO);

    verify(groupMapper).groupDTOToGroup(groupDTO);
    verify(groupRepository).save(group);
  }

  @Test
  void getGroupByIdShouldReturnGroupDTOWhenGroupExists() {
    String id = "existing-id";
    Group group = new Group();
    GroupDTO expectedDTO = new GroupDTO();
    when(groupRepository.findById(id)).thenReturn(Optional.of(group));
    when(groupMapper.groupToGroupDTO(group)).thenReturn(expectedDTO);

    Optional<GroupDTO> result = groupService.getGroupById(id);

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(expectedDTO);
  }

  @Test
  void getGroupByIdShouldReturnEmptyOptionalWhenGroupDoesNotExist() {
    String id = "non-existing-id";
    when(groupRepository.findById(id)).thenReturn(Optional.empty());

    Optional<GroupDTO> result = groupService.getGroupById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllGroupsShouldReturnListOfGroupDTOsWhenGroupsExist() {
    Group group1 = new Group();
    Group group2 = new Group();
    List<Group> groups = asList(group1, group2);
    GroupDTO dto1 = new GroupDTO();
    GroupDTO dto2 = new GroupDTO();

    when(groupRepository.findAll()).thenReturn(groups);
    when(groupMapper.groupToGroupDTO(group1)).thenReturn(dto1);
    when(groupMapper.groupToGroupDTO(group2)).thenReturn(dto2);

    List<GroupDTO> result = groupService.getAllGroups();

    assertThat(result).hasSize(2);
    assertThat(result).containsExactly(dto1, dto2);
  }

  @Test
  void getAllGroupsShouldReturnEmptyListWhenNoGroupsExist() {
    when(groupRepository.findAll()).thenReturn(emptyList());

    List<GroupDTO> result = groupService.getAllGroups();

    assertThat(result).isEmpty();
  }

  @Test
  void getAllGroupsShouldReturnListOfGroupDTOsWhenGroupsExistOnPage() {
    int page = 1;
    int itemsPerPage = 2;
    Group group1 = new Group();
    Group group2 = new Group();
    List<Group> groups = asList(group1, group2);
    Page<Group> pagedGroups = new PageImpl<>(groups, PageRequest.of(page - 1, itemsPerPage),
        groups.size());
    GroupDTO dto1 = new GroupDTO();
    GroupDTO dto2 = new GroupDTO();

    when(groupRepository.findAll(any(Pageable.class))).thenReturn(pagedGroups);
    when(groupMapper.groupToGroupDTO(group1)).thenReturn(dto1);
    when(groupMapper.groupToGroupDTO(group2)).thenReturn(dto2);

    List<GroupDTO> result = groupService.getAllGroups(page, itemsPerPage);

    assertThat(result).hasSize(2);
    assertThat(result).containsExactly(dto1, dto2);
  }

  @Test
  void getAllGroupsShouldReturnEmptyListWhenNoGroupsExistOnPage() {
    int page = 1;
    int itemsPerPage = 2;
    Page<Group> pagedGroups = new PageImpl<>(emptyList(), PageRequest.of(page - 1, itemsPerPage),
        0);

    when(groupRepository.findAll(any(Pageable.class))).thenReturn(pagedGroups);

    List<GroupDTO> result = groupService.getAllGroups(page, itemsPerPage);

    assertThat(result).isEmpty();
  }

  @Test
  void updateGroupShouldCorrectlyMapAndSaveGroup() {
    GroupDTO groupDTO = new GroupDTO();
    Group group = new Group();

    when(groupMapper.groupDTOToGroup(groupDTO)).thenReturn(group);

    groupService.updateGroup(groupDTO);

    verify(groupMapper).groupDTOToGroup(groupDTO);
    verify(groupRepository).save(group);
  }

  @Test
  void deleteGroupShouldWorkCorrectlyIfGroupExists() {
    String groupId = "existing-id";

    when(groupRepository.existsById(groupId)).thenReturn(true);

    boolean result = groupService.deleteGroup(groupId);

    assertThat(result).isTrue();
    verify(groupRepository).deleteById(groupId);
  }

  @Test
  void deleteGroupShouldReturnFalseIfGroupNotExists() {
    String groupId = "non-existing-id";

    when(groupRepository.existsById(groupId)).thenReturn(false);

    boolean result = groupService.deleteGroup(groupId);

    assertThat(result).isFalse();
  }

}
