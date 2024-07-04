package ua.foxminded.universitycms.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.mapping.CourseAssignmentMapper;
import ua.foxminded.universitycms.mapping.GroupMapper;
import ua.foxminded.universitycms.mapping.user.role.StudentMapper;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.GroupStatus;
import ua.foxminded.universitycms.model.entity.user.Gender;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;
import ua.foxminded.universitycms.repository.CourseAssignmentRepository;
import ua.foxminded.universitycms.repository.GroupRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.StudentDataRepository;
import ua.foxminded.universitycms.service.exception.GroupAlreadyExistsException;
import ua.foxminded.universitycms.service.exception.GroupNotFoundException;
import ua.foxminded.universitycms.service.exception.InvalidCourseAssignmentException;
import ua.foxminded.universitycms.service.exception.InvalidGroupStatusException;
import ua.foxminded.universitycms.service.exception.InvalidStudentException;
import ua.foxminded.universitycms.service.impl.GroupServiceImpl;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

  @Mock
  private GroupRepository groupRepository;

  @Mock
  private GroupMapper groupMapper;

  @InjectMocks
  private GroupServiceImpl groupService;

  @Mock
  private CourseAssignmentRepository courseAssignmentRepository;

  @Mock
  private CourseAssignmentMapper courseAssignmentMapper;

  @Mock
  private StudentDataRepository studentDataRepository;

  @Mock
  private StudentMapper studentMapper;

  @Test
  void addGroupShouldSaveNewGroupWhenNameDoesNotExist() {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setName("New Group");

    Group group = new Group();
    group.setId("generated-id");
    group.setName("New Group");

    when(groupRepository.existsByNameIgnoreCase(groupDTO.getName())).thenReturn(false);
    when(groupMapper.groupDTOToGroup(groupDTO)).thenReturn(group);
    when(groupRepository.save(group)).thenReturn(group);

    groupService.addGroup(groupDTO);

    verify(groupRepository).existsByNameIgnoreCase(groupDTO.getName());
    verify(groupMapper).groupDTOToGroup(groupDTO);
    verify(groupRepository).save(group);
  }

  @Test
  void addGroupShouldThrowExceptionWhenGroupNameAlreadyExists() {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setName("Existing Group");

    when(groupRepository.existsByNameIgnoreCase(groupDTO.getName())).thenReturn(true);

    assertThatThrownBy(() -> groupService.addGroup(groupDTO))
        .isInstanceOf(GroupAlreadyExistsException.class)
        .hasMessageContaining("Group with name Existing Group already exists");

    verify(groupRepository).existsByNameIgnoreCase(groupDTO.getName());
    verify(groupMapper, never()).groupDTOToGroup(any(GroupDTO.class));
    verify(groupRepository, never()).save(any(Group.class));
  }

  @Test
  void addGroupShouldHandleGroupNameCaseInsensitively() {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setName("New Group");

    when(groupRepository.existsByNameIgnoreCase(
        argThat(name -> name.equalsIgnoreCase("New Group")))).thenReturn(true);

    assertThatThrownBy(() -> groupService.addGroup(groupDTO))
        .isInstanceOf(GroupAlreadyExistsException.class)
        .hasMessageContaining("Group with name New Group already exists");

    verify(groupRepository).existsByNameIgnoreCase("New Group");
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
    assertThat(expectedDTO).isEqualTo(result.get());
  }

  @Test
  void getGroupByIdShouldReturnEmptyOptionalWhenGroupDoesNotExist() {
    String id = "non-existing-id";
    when(groupRepository.findById(id)).thenReturn(Optional.empty());

    Optional<GroupDTO> result = groupService.getGroupById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getGroupByIdShouldLogStudentInfoWhenStudentsPresentInGroup() {
    Logger logger = (Logger) LoggerFactory.getLogger(GroupServiceImpl.class);
    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    logger.addAppender(listAppender);
    logger.setLevel(Level.INFO);

    String groupId = "group1";
    Group group = Group.builder()
        .withId(groupId)
        .withName("Test Group")
        .withStudents(new HashSet<>())
        .build();
    StudentData student1 = StudentData.builder()
        .withId("student1")
        .withUser(User.builder().id("user1").firstName("John").lastName("Doe").build())
        .build();
    group.getStudents().add(student1);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(groupMapper.groupToGroupDTO(any(Group.class))).thenReturn(new GroupDTO());

    groupService.getGroupById(groupId);

    verify(groupRepository).findById(groupId);
    verify(groupMapper).groupToGroupDTO(group);

    assertThat(listAppender.list)
        .extracting(ILoggingEvent::getFormattedMessage)
        .containsExactly(
            "Fetching group with id group1.",
            "Group Test Group has 1 students.",
            "Student ID: user1, Name: John Doe"
        );

    logger.detachAppender(listAppender);
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

    assertThat(result).hasSize(2).containsExactly(dto1, dto2);
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

    assertThat(result).hasSize(2).containsExactly(dto1, dto2);
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
  void getAllActiveGroupsShouldReturnOnlyGroupsWithActiveStatusIfTheyExist() {
    Group group1 = Group.builder().withId("1").withStatus(GroupStatus.ACTIVE).build();
    Group group2 = Group.builder().withId("2").withStatus(GroupStatus.ACTIVE).build();
    List<Group> groups = asList(group1, group2);
    GroupDTO dto1 = GroupDTO.builder().id(group1.getId()).status(group1.getStatus()).build();
    GroupDTO dto2 = GroupDTO.builder().id(group2.getId()).status(group2.getStatus()).build();

    when(groupRepository.findAllByStatusWithStudents(GroupStatus.ACTIVE)).thenReturn(groups);
    when(groupMapper.groupToGroupDTO(group1)).thenReturn(dto1);
    when(groupMapper.groupToGroupDTO(group2)).thenReturn(dto2);

    List<GroupDTO> activeGroups = groupService.getAllActiveGroups();

    assertThat(activeGroups).hasSize(2).containsExactly(dto1, dto2);
  }

  @Test
  void getGroupsBasedOnUserDetailsShouldReturnAllGroupsWhenUserIsAdmin() {
    UserDetails adminUser = mock(UserDetails.class);
    Collection<? extends GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));

    doReturn(authorities).when(adminUser).getAuthorities();

    List<Group> allGroups = Arrays.asList(new Group(), new Group(), new Group());
    List<GroupDTO> allGroupDTOs = Arrays.asList(new GroupDTO(), new GroupDTO(), new GroupDTO());

    when(groupRepository.findAll()).thenReturn(allGroups);
    when(groupMapper.groupToGroupDTO(any(Group.class))).thenReturn(new GroupDTO());

    List<GroupDTO> result = groupService.getGroupsBasedOnUserDetails(adminUser);

    assertThat(result).hasSize(3).isEqualTo(allGroupDTOs);
    verify(groupRepository).findAll();
    verify(groupMapper, times(3)).groupToGroupDTO(any(Group.class));
  }

  @Test
  void getGroupsBasedOnUserDetailsShouldReturnActiveGroupsWhenUserIsNotAdmin() {
    UserDetails regularUser = mock(UserDetails.class);
    Collection<? extends GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    doReturn(authorities).when(regularUser).getAuthorities();

    Group group1 = Group.builder().withId("1").build();
    Group group2 = Group.builder().withId("2").build();
    List<Group> activeGroups = Arrays.asList(group1, group2);

    GroupDTO groupDTO1 = GroupDTO.builder().id("1").build();
    GroupDTO groupDTO2 = GroupDTO.builder().id("2").build();
    List<GroupDTO> activeGroupDTOs = Arrays.asList(groupDTO1, groupDTO2);

    when(groupRepository.findAllByStatusWithStudents(GroupStatus.ACTIVE)).thenReturn(activeGroups);
    when(groupMapper.groupToGroupDTO(group1)).thenReturn(groupDTO1);
    when(groupMapper.groupToGroupDTO(group2)).thenReturn(groupDTO2);

    List<GroupDTO> result = groupService.getGroupsBasedOnUserDetails(regularUser);

    assertThat(result).hasSize(2).isEqualTo(activeGroupDTOs);
    verify(groupRepository).findAllByStatusWithStudents(GroupStatus.ACTIVE);
    verify(groupMapper, times(2)).groupToGroupDTO(any(Group.class));
  }

  @Test
  void getGroupsBasedOnUserDetailsShouldReturnAllActiveGroupsWhenUserDetailsIsNull() {
    Group group1 = Group.builder().withId("1").build();
    Group group2 = Group.builder().withId("2").build();
    List<Group> activeGroups = Arrays.asList(group1, group2);

    GroupDTO groupDTO1 = GroupDTO.builder().id("1").build();
    GroupDTO groupDTO2 = GroupDTO.builder().id("2").build();
    List<GroupDTO> activeGroupDTOs = Arrays.asList(groupDTO1, groupDTO2);

    when(groupRepository.findAllByStatusWithStudents(GroupStatus.ACTIVE)).thenReturn(activeGroups);
    when(groupMapper.groupToGroupDTO(group1)).thenReturn(groupDTO1);
    when(groupMapper.groupToGroupDTO(group2)).thenReturn(groupDTO2);

    List<GroupDTO> result = groupService.getGroupsBasedOnUserDetails(null);

    assertThat(result).hasSize(2).isEqualTo(activeGroupDTOs);
    verify(groupRepository).findAllByStatusWithStudents(GroupStatus.ACTIVE);
    verify(groupMapper, times(2)).groupToGroupDTO(any(Group.class));
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
  void deleteGroupShouldDeleteGroupWhenGroupExistsAndStatusIsDeleted() {
    String groupId = "test-id";
    Group group = Group.builder()
        .withId(groupId)
        .withStatus(GroupStatus.DELETED)
        .build();

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

    groupService.deleteGroup(groupId);

    verify(groupRepository).delete(group);
  }

  @Test
  void deleteGroupShouldThrowGroupNotFoundExceptionWhenGroupDoesNotExist() {
    String groupId = "non-existent-id";

    when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> groupService.deleteGroup(groupId))
        .isInstanceOf(GroupNotFoundException.class)
        .hasMessageContaining("Group not found with ID: " + groupId);

    verify(groupRepository, never()).delete(any(Group.class));
  }

  @Test
  void deleteGroupShouldThrowInvalidGroupStatusExceptionWhenGroupStatusIsNotDeleted() {
    String groupId = "test-id";
    Group group = Group.builder()
        .withId(groupId)
        .withStatus(GroupStatus.ACTIVE)
        .build();

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

    assertThatThrownBy(() -> groupService.deleteGroup(groupId))
        .isInstanceOf(InvalidGroupStatusException.class)
        .hasMessageContaining("Group status must be DELETED to delete the group");

    verify(groupRepository, never()).delete(any(Group.class));
  }

  @Test
  void getCourseAssignmentWithNoGroupShouldReturnMappedDTOs() {
    CourseAssignment assignment1 = CourseAssignment.builder().withId("1").build();
    CourseAssignment assignment2 = CourseAssignment.builder().withId("2").build();
    Set<CourseAssignment> assignments = new HashSet<>();
    assignments.add(assignment1);
    assignments.add(assignment2);

    CourseAssignmentDTO dto1 = CourseAssignmentDTO.builder().id(assignment1.getId()).build();
    CourseAssignmentDTO dto2 = CourseAssignmentDTO.builder().id(assignment1.getId()).build();

    when(courseAssignmentRepository.findCourseAssignmentsWithNoGroup()).thenReturn(assignments);
    when(courseAssignmentMapper.courseAssignmentToCourseAssignmentDTO(assignment1)).thenReturn(
        dto1);
    when(courseAssignmentMapper.courseAssignmentToCourseAssignmentDTO(assignment2)).thenReturn(
        dto2);

    List<CourseAssignmentDTO> result = groupService.getCourseAssignmentWithNoGroup();

    assertThat(result).hasSize(2)
        .containsExactlyInAnyOrder(dto1, dto2);

    verify(courseAssignmentRepository).findCourseAssignmentsWithNoGroup();
    verify(courseAssignmentMapper, times(2)).courseAssignmentToCourseAssignmentDTO(
        any(CourseAssignment.class));
  }

  @Test
  void getCourseAssignmentWithNoGroupShouldReturnEmptyListWhenNoAssignments() {
    when(courseAssignmentRepository.findCourseAssignmentsWithNoGroup()).thenReturn(new HashSet<>());

    List<CourseAssignmentDTO> result = groupService.getCourseAssignmentWithNoGroup();

    assertThat(result).isEmpty();

    verify(courseAssignmentRepository).findCourseAssignmentsWithNoGroup();
    verify(courseAssignmentMapper, never()).courseAssignmentToCourseAssignmentDTO(
        any(CourseAssignment.class));
  }

  @Test
  void getGroupEditDetailsShouldReturnGroupWithAvailableAssignmentsAndStudents() {
    String groupId = "group1";
    Group group = new Group();
    group.setId(groupId);
    group.setName("Test Group");
    group.setStudents(new HashSet<>());

    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setId(groupId);
    groupDTO.setName("Test Group");

    CourseAssignment assignment1 = CourseAssignment.builder().withId("1").build();
    CourseAssignment assignment2 = CourseAssignment.builder().withId("2").build();
    Set<CourseAssignment> availableAssignments = new HashSet<>(
        Arrays.asList(assignment1, assignment2));

    CourseAssignmentDTO assignmentDTO1 = CourseAssignmentDTO.builder().id(assignment1.getId())
        .build();
    CourseAssignmentDTO assignmentDTO2 = CourseAssignmentDTO.builder().id(assignment2.getId())
        .build();
    List<CourseAssignmentDTO> availableAssignmentDTOs = Arrays.asList(assignmentDTO1,
        assignmentDTO2);

    StudentData student1 = StudentData.builder().withId("1").build();
    StudentData student2 = StudentData.builder().withId("2").build();
    Set<StudentData> availableStudents = new HashSet<>(Arrays.asList(student1, student2));

    StudentResponseDTO studentDTO1 = StudentResponseDTO.builder().id(student1.getId()).build();
    StudentResponseDTO studentDTO2 = StudentResponseDTO.builder().id(student2.getId()).build();
    List<StudentResponseDTO> availableStudentDTOs = Arrays.asList(studentDTO1, studentDTO2);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(groupMapper.groupToGroupDTO(group)).thenReturn(groupDTO);
    when(courseAssignmentRepository.findCourseAssignmentsWithNoGroup()).thenReturn(
        availableAssignments);
    when(studentDataRepository.findStudentsWithNoGroup()).thenReturn(availableStudents);

    when(courseAssignmentMapper.courseAssignmentToCourseAssignmentDTO(assignment1)).thenReturn(
        assignmentDTO1);
    when(courseAssignmentMapper.courseAssignmentToCourseAssignmentDTO(assignment2)).thenReturn(
        assignmentDTO2);
    when(studentMapper.mapToStudentResponseDTO(student1)).thenReturn(studentDTO1);
    when(studentMapper.mapToStudentResponseDTO(student2)).thenReturn(studentDTO2);

    GroupDTO result = groupService.getGroupEditDetails(groupId);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(groupId);
    assertThat(result.getName()).isEqualTo("Test Group");
    assertThat(result.getAvailableCourseAssignments()).containsExactlyInAnyOrderElementsOf(
        availableAssignmentDTOs);
    assertThat(result.getAvailableStudents()).containsExactlyInAnyOrderElementsOf(
        availableStudentDTOs);

    verify(groupRepository).findById(groupId);
    verify(groupMapper).groupToGroupDTO(group);
    verify(courseAssignmentRepository).findCourseAssignmentsWithNoGroup();
    verify(studentDataRepository).findStudentsWithNoGroup();
    verify(courseAssignmentMapper, times(2)).courseAssignmentToCourseAssignmentDTO(
        any(CourseAssignment.class));
    verify(studentMapper, times(2)).mapToStudentResponseDTO(any(StudentData.class));
  }

  @Test
  void getGroupEditDetailsShouldThrowExceptionWhenGroupNotFound() {
    String groupId = "nonexistent";
    when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> groupService.getGroupEditDetails(groupId))
        .isInstanceOf(GroupNotFoundException.class)
        .hasMessageContaining("Group not found with ID: " + groupId);
  }

  @Test
  void getGroupEditDetailsShouldReturnGroupWithEmptyAvailableStudents() {
    String groupId = "group1";
    Group group = Group.builder()
        .withId(groupId)
        .withName("Test Group")
        .withStudents(new HashSet<>())
        .build();

    GroupDTO groupDTO = GroupDTO.builder()
        .id(groupId)
        .name("Test Group")
        .build();

    CourseAssignment assignment = CourseAssignment.builder()
        .withId("assignment1").build();
    Set<CourseAssignment> availableAssignments = new HashSet<>(
        Collections.singletonList(assignment));

    CourseAssignmentDTO assignmentDTO = CourseAssignmentDTO.builder()
        .id("assignment1")
        .build();

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(groupMapper.groupToGroupDTO(group)).thenReturn(groupDTO);
    when(courseAssignmentRepository.findCourseAssignmentsWithNoGroup()).thenReturn(
        availableAssignments);
    when(studentDataRepository.findStudentsWithNoGroup()).thenReturn(new HashSet<>());
    when(courseAssignmentMapper.courseAssignmentToCourseAssignmentDTO(assignment)).thenReturn(
        assignmentDTO);

    GroupDTO result = groupService.getGroupEditDetails(groupId);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(groupId);
    assertThat(result.getName()).isEqualTo("Test Group");
    assertThat(result.getAvailableCourseAssignments()).containsExactly(assignmentDTO);
    assertThat(result.getAvailableStudents()).isEmpty();

    verify(groupRepository).findById(groupId);
    verify(groupMapper).groupToGroupDTO(group);
    verify(courseAssignmentRepository).findCourseAssignmentsWithNoGroup();
    verify(studentDataRepository).findStudentsWithNoGroup();
    verify(courseAssignmentMapper).courseAssignmentToCourseAssignmentDTO(assignment);
  }

  @Test
  void getGroupEditDetailsShouldReturnGroupWithEmptyAvailableAssignments() {
    String groupId = "group1";
    Group group = Group.builder()
        .withId(groupId)
        .withName("Test Group")
        .withStudents(new HashSet<>())
        .build();

    GroupDTO groupDTO = GroupDTO.builder()
        .id(groupId)
        .name("Test Group")
        .build();

    User studentUser = User.builder()
        .id("student1")
        .firstName("Alice")
        .lastName("Johnson")
        .email("alice@example.com")
        .gender(Gender.FEMALE)
        .build();

    StudentData student = StudentData.builder()
        .withId("student1")
        .withUser(studentUser)
        .build();

    Set<StudentData> availableStudents = new HashSet<>(Collections.singletonList(student));

    StudentResponseDTO studentDTO = StudentResponseDTO.builder()
        .id("student1")
        .firstName("Alice")
        .lastName("Johnson")
        .email("alice@example.com")
        .gender(Gender.FEMALE)
        .build();

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(groupMapper.groupToGroupDTO(group)).thenReturn(groupDTO);
    when(courseAssignmentRepository.findCourseAssignmentsWithNoGroup()).thenReturn(new HashSet<>());
    when(studentDataRepository.findStudentsWithNoGroup()).thenReturn(availableStudents);
    when(studentMapper.mapToStudentResponseDTO(student)).thenReturn(studentDTO);

    GroupDTO result = groupService.getGroupEditDetails(groupId);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(groupId);
    assertThat(result.getName()).isEqualTo("Test Group");
    assertThat(result.getAvailableCourseAssignments()).isEmpty();
    assertThat(result.getAvailableStudents()).containsExactly(studentDTO);

    verify(groupRepository).findById(groupId);
    verify(groupMapper).groupToGroupDTO(group);
    verify(courseAssignmentRepository).findCourseAssignmentsWithNoGroup();
    verify(studentDataRepository).findStudentsWithNoGroup();
    verify(studentMapper).mapToStudentResponseDTO(student);
  }

  @Test
  void addStudentToGroupShouldSuccessfullyAddStudentWhenGroupAndStudentExist() {
    String groupId = "group1";
    String studentId = "student1";
    Group group = Group.builder().withId(groupId).withStudents(new HashSet<>()).build();
    StudentData student = StudentData.builder().withId(studentId).build();

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(studentDataRepository.findById(studentId)).thenReturn(Optional.of(student));

    groupService.addStudentToGroup(groupId, studentId);

    assertThat(student.getOwnerGroup()).isEqualTo(group);
    assertThat(group.getStudents()).contains(student);
    verify(studentDataRepository).save(student);
    verify(groupRepository).save(group);
  }

  @Test
  void addCourseAssignmentToGroupShouldSuccessfullyAddAssignmentWhenGroupAndAssignmentExist() {
    String groupId = "group1";
    String assignmentId = "assignment1";
    Group group = new Group();
    group.setId(groupId);
    group.setCourseAssignments(new HashSet<>());
    CourseAssignment assignment = new CourseAssignment();
    assignment.setId(assignmentId);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(courseAssignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

    groupService.addCourseAssignmentToGroup(groupId, assignmentId);

    assertThat(assignment.getGroup()).isEqualTo(group);
    assertThat(group.getCourseAssignments()).contains(assignment);
    verify(courseAssignmentRepository).save(assignment);
    verify(groupRepository).save(group);
  }

  @Test
  void addCourseAssignmentToGroupShouldThrowGroupNotFoundExceptionWhenGroupDoesNotExist() {
    String groupId = "nonexistent";
    String assignmentId = "assignment1";

    when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> groupService.addCourseAssignmentToGroup(groupId, assignmentId))
        .isInstanceOf(GroupNotFoundException.class)
        .hasMessageContaining("Group not found with ID: " + groupId);

    verify(courseAssignmentRepository, never()).findById(anyString());
    verify(courseAssignmentRepository, never()).save(any(CourseAssignment.class));
    verify(groupRepository, never()).save(any(Group.class));
  }

  @Test
  void addCourseAssignmentToGroupShouldThrowInvalidCourseAssignmentExceptionWhenAssignmentDoesNotExist() {
    String groupId = "group1";
    String assignmentId = "nonexistent";
    Group group = new Group();
    group.setId(groupId);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(courseAssignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> groupService.addCourseAssignmentToGroup(groupId, assignmentId))
        .isInstanceOf(InvalidCourseAssignmentException.class)
        .hasMessageContaining("Invalid course assignment ID: " + assignmentId);

    verify(courseAssignmentRepository, never()).save(any(CourseAssignment.class));
    verify(groupRepository, never()).save(any(Group.class));
  }

  @Test
  void addCourseAssignmentToGroupShouldNotAddAssignmentWhenAssignmentAlreadyInGroup() {
    String groupId = "group1";
    String assignmentId = "assignment1";
    Group group = new Group();
    group.setId(groupId);
    CourseAssignment assignment = new CourseAssignment();
    assignment.setId(assignmentId);
    assignment.setGroup(group);

    HashSet<CourseAssignment> assignments = new HashSet<>();
    assignments.add(assignment);
    group.setCourseAssignments(assignments);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(courseAssignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

    groupService.addCourseAssignmentToGroup(groupId, assignmentId);

    assertThat(assignment.getGroup()).isEqualTo(group);
    assertThat(group.getCourseAssignments()).containsExactly(assignment);
    verify(courseAssignmentRepository, never()).save(any(CourseAssignment.class));
    verify(groupRepository, never()).save(any(Group.class));
  }

  @Test
  void removeCourseAssignmentFromGroupShouldSuccessfullyRemoveAssignment() {
    String assignmentId = "assignment1";
    Group group = new Group();
    group.setId("group1");
    CourseAssignment assignment = new CourseAssignment();
    assignment.setId(assignmentId);
    assignment.setGroup(group);

    when(courseAssignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

    groupService.removeCourseAssignmentFromGroup(assignmentId);

    assertThat(assignment.getGroup()).isNull();
    verify(courseAssignmentRepository).save(assignment);
  }

  @Test
  void removeCourseAssignmentFromGroupShouldThrowExceptionWhenAssignmentNotFound() {
    String assignmentId = "nonexistent";
    when(courseAssignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> groupService.removeCourseAssignmentFromGroup(assignmentId))
        .isInstanceOf(InvalidCourseAssignmentException.class)
        .hasMessageContaining("Invalid course assignment ID: " + assignmentId);

    verify(courseAssignmentRepository, never()).save(any(CourseAssignment.class));
  }

  @Test
  void removeCourseAssignmentFromGroupShouldHandleAssignmentAlreadyWithoutGroup() {
    String assignmentId = "assignment1";
    CourseAssignment assignment = new CourseAssignment();
    assignment.setId(assignmentId);
    assignment.setGroup(null);

    when(courseAssignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

    groupService.removeCourseAssignmentFromGroup(assignmentId);

    assertThat(assignment.getGroup()).isNull();
    verify(courseAssignmentRepository).save(assignment);
  }

  @Test
  void addStudentToGroupShouldThrowGroupNotFoundExceptionWhenGroupDoesNotExist() {
    String groupId = "nonexistent";
    String studentId = "student1";

    when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> groupService.addStudentToGroup(groupId, studentId))
        .isInstanceOf(GroupNotFoundException.class)
        .hasMessageContaining("Group not found with ID: " + groupId);

    verify(studentDataRepository, never()).findById(anyString());
    verify(studentDataRepository, never()).save(any(StudentData.class));
    verify(groupRepository, never()).save(any(Group.class));
  }

  @Test
  void addStudentToGroupShouldThrowInvalidStudentExceptionWhenStudentDoesNotExist() {
    String groupId = "group1";
    String studentId = "nonexistent";
    Group group = Group.builder().withId(groupId).withStudents(new HashSet<>()).build();

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(studentDataRepository.findById(studentId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> groupService.addStudentToGroup(groupId, studentId))
        .isInstanceOf(InvalidStudentException.class)
        .hasMessageContaining("Invalid student ID: " + studentId);

    verify(studentDataRepository, never()).save(any(StudentData.class));
    verify(groupRepository, never()).save(any(Group.class));
  }

  @Test
  void addStudentToGroupShouldNotAddStudentWhenStudentAlreadyInGroup() {
    String groupId = "group1";
    String studentId = "student1";
    Group group = Group.builder().withId(groupId).withStudents(new HashSet<>()).build();
    StudentData student = StudentData.builder().withId(studentId).withOwnerGroup(group).build();
    group.getStudents().add(student);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(studentDataRepository.findById(studentId)).thenReturn(Optional.of(student));

    groupService.addStudentToGroup(groupId, studentId);

    assertThat(student.getOwnerGroup()).isEqualTo(group);
    assertThat(group.getStudents()).containsExactly(student);
    verify(studentDataRepository, never()).save(any(StudentData.class));
    verify(groupRepository, never()).save(any(Group.class));
  }

  @Test
  void addStudentToGroupShouldAddStudentWhenStudentHasNoGroup() {
    String groupId = "group1";
    String studentId = "student1";
    Group group = Group.builder()
        .withId(groupId)
        .withName("Test Group")
        .withStudents(new HashSet<>())
        .build();
    StudentData student = StudentData.builder()
        .withId(studentId)
        .withOwnerGroup(null)
        .withUser(User.builder().firstName("John").lastName("Doe").build())
        .build();

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(studentDataRepository.findById(studentId)).thenReturn(Optional.of(student));

    groupService.addStudentToGroup(groupId, studentId);

    assertThat(student.getOwnerGroup()).isEqualTo(group);
    assertThat(group.getStudents()).contains(student);
    verify(studentDataRepository).save(student);
    verify(groupRepository).save(group);
  }

  @Test
  void addStudentToGroupShouldChangeGroupWhenStudentHasDifferentGroup() {
    String groupId = "group1";
    String studentId = "student1";
    Group newGroup = Group.builder()
        .withId(groupId)
        .withName("New Group")
        .withStudents(new HashSet<>())
        .build();
    Group oldGroup = Group.builder()
        .withId("oldGroup")
        .withName("Old Group")
        .withStudents(new HashSet<>())
        .build();
    StudentData student = StudentData.builder()
        .withId(studentId)
        .withOwnerGroup(oldGroup)
        .withUser(User.builder().firstName("John").lastName("Doe").build())
        .build();
    oldGroup.getStudents().add(student);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(newGroup));
    when(studentDataRepository.findById(studentId)).thenReturn(Optional.of(student));

    groupService.addStudentToGroup(groupId, studentId);

    assertThat(student.getOwnerGroup()).isEqualTo(newGroup);
    assertThat(newGroup.getStudents()).contains(student);
    verify(studentDataRepository).save(student);
    verify(groupRepository).save(newGroup);
  }

  @Test
  void addStudentToGroupShouldDoNothingWhenStudentAlreadyInSpecifiedGroup() {
    String groupId = "group1";
    String studentId = "student1";
    Group group = Group.builder()
        .withId(groupId)
        .withName("Test Group")
        .withStudents(new HashSet<>())
        .build();
    StudentData student = StudentData.builder()
        .withId(studentId)
        .withOwnerGroup(group)
        .withUser(User.builder().firstName("John").lastName("Doe").build())
        .build();
    group.getStudents().add(student);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(studentDataRepository.findById(studentId)).thenReturn(Optional.of(student));

    groupService.addStudentToGroup(groupId, studentId);

    assertThat(student.getOwnerGroup()).isEqualTo(group);
    assertThat(group.getStudents()).containsExactly(student);
    verify(studentDataRepository, never()).save(any(StudentData.class));
    verify(groupRepository, never()).save(any(Group.class));
  }

  @Test
  void removeStudentFromGroupShouldSuccessfullyRemoveStudent() {
    String studentId = "student1";
    Group group = new Group();
    group.setId("group1");
    StudentData studentData = new StudentData();
    studentData.setId(studentId);
    studentData.setOwnerGroup(group);

    when(studentDataRepository.findById(studentId)).thenReturn(Optional.of(studentData));

    groupService.removeStudentFromGroup(studentId);

    assertThat(studentData.getOwnerGroup()).isNull();
    verify(studentDataRepository).save(studentData);
  }

  @Test
  void removeStudentFromGroupShouldThrowExceptionWhenStudentNotFound() {
    String studentId = "nonexistent";
    when(studentDataRepository.findById(studentId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> groupService.removeStudentFromGroup(studentId))
        .isInstanceOf(InvalidStudentException.class)
        .hasMessageContaining("Invalid student ID: " + studentId);

    verify(studentDataRepository, never()).save(any(StudentData.class));
  }

  @Test
  void removeStudentFromGroupShouldHandleStudentAlreadyWithoutGroup() {
    String studentId = "student1";
    StudentData studentData = new StudentData();
    studentData.setId(studentId);
    studentData.setOwnerGroup(null);

    when(studentDataRepository.findById(studentId)).thenReturn(Optional.of(studentData));

    groupService.removeStudentFromGroup(studentId);

    assertThat(studentData.getOwnerGroup()).isNull();
    verify(studentDataRepository, never()).save(any(StudentData.class));
  }

  @Test
  void changeGroupStatusShouldChangeStatusAndReturnNewStatusWhenGroupExists() {
    String groupId = "testId";
    Group group = new Group();
    group.setId(groupId);
    group.setStatus(GroupStatus.NEW);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(groupRepository.save(any(Group.class))).thenReturn(group);

    GroupStatus newStatus = groupService.changeGroupStatus(groupId, GroupStatus.ACTIVE);

    assertThat(newStatus).isEqualTo(GroupStatus.ACTIVE);
    assertThat(group.getStatus()).isEqualTo(GroupStatus.ACTIVE);
    verify(groupRepository).save(group);
  }

  @Test
  void changeGroupStatusShouldThrowGroupNotFoundExceptionWhenGroupDoesNotExist() {
    String groupId = "nonExistentId";
    when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> groupService.changeGroupStatus(groupId, GroupStatus.ACTIVE))
        .isInstanceOf(GroupNotFoundException.class)
        .hasMessageContaining("Group not found with ID: " + groupId);
  }

  @Test
  void changeGroupStatusShouldNotChangeStatusOrSaveGroupWhenNewStatusIsSameAsCurrentStatus() {
    String groupId = "testId";
    Group group = new Group();
    group.setId(groupId);
    group.setStatus(GroupStatus.ACTIVE);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

    GroupStatus newStatus = groupService.changeGroupStatus(groupId, GroupStatus.ACTIVE);

    assertThat(newStatus).isEqualTo(GroupStatus.ACTIVE);
    assertThat(group.getStatus()).isEqualTo(GroupStatus.ACTIVE);
    verify(groupRepository, never()).save(any(Group.class));
  }

  @Test
  void changeGroupStatusShouldChangeStatusForAllPossibleTransitionsWhenCalledWithDifferentStatuses() {
    String groupId = "testId";
    Group group = new Group();
    group.setId(groupId);

    when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
    when(groupRepository.save(any(Group.class))).thenReturn(group);

    for (GroupStatus fromStatus : GroupStatus.values()) {
      for (GroupStatus toStatus : GroupStatus.values()) {
        group.setStatus(fromStatus);
        GroupStatus newStatus = groupService.changeGroupStatus(groupId, toStatus);
        assertThat(newStatus).isEqualTo(toStatus);
        assertThat(group.getStatus()).isEqualTo(toStatus);
      }
    }
  }

}
