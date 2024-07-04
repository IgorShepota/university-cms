package ua.foxminded.universitycms.controller;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.foxminded.universitycms.controller.config.SecurityConfig;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.model.entity.GroupStatus;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.exception.GroupAlreadyExistsException;

@WebMvcTest(GroupController.class)
@Import(SecurityConfig.class)
class GroupControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GroupService groupService;

  @Test
  @WithMockUser(username = "admin@example.com", roles = "ADMIN")
  void listGroupsShouldReturnGroupsViewForAdmin() throws Exception {
    List<GroupDTO> groups = Arrays.asList(
        GroupDTO.builder().id("1").name("Group 1").status(GroupStatus.ACTIVE).build(),
        GroupDTO.builder().id("2").name("Group 2").status(GroupStatus.ACTIVE).build()
    );

    ArgumentCaptor<UserDetails> userDetailsCaptor = ArgumentCaptor.forClass(UserDetails.class);
    when(groupService.getGroupsBasedOnUserDetails(userDetailsCaptor.capture())).thenReturn(groups);

    MvcResult result = mockMvc.perform(get("/groups"))
        .andExpect(status().isOk())
        .andExpect(view().name("groups"))
        .andExpect(model().attributeExists("groups"))
        .andExpect(model().attributeExists("group"))
        .andReturn();

    verify(groupService).getGroupsBasedOnUserDetails(userDetailsCaptor.getValue());

    UserDetails capturedUserDetails = userDetailsCaptor.getValue();
    assertThat(capturedUserDetails.getUsername()).isEqualTo("admin@example.com");
    assertThat(capturedUserDetails.getAuthorities())
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    List<GroupDTO> modelGroups = (List<GroupDTO>) result.getModelAndView().getModel().get("groups");
    assertThat(modelGroups).hasSize(2);
    assertThat(modelGroups).containsExactlyElementsOf(groups);

    GroupDTO firstGroup = modelGroups.get(0);
    assertThat(firstGroup.getId()).isEqualTo("1");
    assertThat(firstGroup.getName()).isEqualTo("Group 1");
    assertThat(firstGroup.getStatus()).isEqualTo(GroupStatus.ACTIVE);

    GroupDTO secondGroup = modelGroups.get(1);
    assertThat(secondGroup.getId()).isEqualTo("2");
    assertThat(secondGroup.getName()).isEqualTo("Group 2");
    assertThat(secondGroup.getStatus()).isEqualTo(GroupStatus.ACTIVE);

    Object modelGroupAttribute = result.getModelAndView().getModel().get("group");
    assertThat(modelGroupAttribute).isInstanceOf(GroupDTO.class);
  }

  @Test
  @WithMockUser(username = "user@example.com", roles = "USER")
  void listGroupsShouldReturnGroupsViewForRegularUser() throws Exception {
    List<CourseAssignmentDTO> courseAssignments = Arrays.asList(
        CourseAssignmentDTO.builder().id("1").build(),
        CourseAssignmentDTO.builder().id("2").build()
    );

    GroupDTO groupDTO = GroupDTO.builder()
        .id("1")
        .name("Group 1")
        .status(GroupStatus.ACTIVE)
        .courseAssignments(courseAssignments)
        .build();

    List<GroupDTO> groups = Collections.singletonList(groupDTO);

    ArgumentCaptor<UserDetails> userDetailsCaptor = ArgumentCaptor.forClass(UserDetails.class);
    when(groupService.getGroupsBasedOnUserDetails(userDetailsCaptor.capture())).thenReturn(groups);

    MvcResult result = mockMvc.perform(get("/groups"))
        .andExpect(status().isOk())
        .andExpect(view().name("groups"))
        .andExpect(model().attributeExists("groups"))
        .andExpect(model().attributeExists("group"))
        .andReturn();

    verify(groupService).getGroupsBasedOnUserDetails(userDetailsCaptor.getValue());

    UserDetails capturedUserDetails = userDetailsCaptor.getValue();
    assertThat(capturedUserDetails.getUsername()).isEqualTo("user@example.com");
    assertThat(capturedUserDetails.getAuthorities())
        .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

    List<GroupDTO> modelGroups = (List<GroupDTO>) result.getModelAndView().getModel().get("groups");
    assertThat(modelGroups).hasSize(1);
    GroupDTO modelGroup = modelGroups.get(0);
    assertThat(modelGroup.getId()).isEqualTo("1");
    assertThat(modelGroup.getName()).isEqualTo("Group 1");
    assertThat(modelGroup.getStatus()).isEqualTo(GroupStatus.ACTIVE);
    assertThat(modelGroup.getCourseAssignments()).hasSize(2);

    Object modelGroupAttribute = result.getModelAndView().getModel().get("group");
    assertThat(modelGroupAttribute).isInstanceOf(GroupDTO.class);
  }

  @Test
  @WithAnonymousUser
  void listGroupsShouldReturnGroupsViewForAnonymousUser() throws Exception {
    List<GroupDTO> groups = Collections.emptyList();
    when(groupService.getGroupsBasedOnUserDetails(null)).thenReturn(groups);

    mockMvc.perform(get("/groups"))
        .andExpect(status().isOk())
        .andExpect(view().name("groups"))
        .andExpect(model().attribute("groups", groups))
        .andExpect(model().attributeExists("group"))
        .andExpect(model().attribute("group", instanceOf(GroupDTO.class)));

    verify(groupService).getGroupsBasedOnUserDetails(null);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void listGroupsShouldHandleEmptyGroupList() throws Exception {
    List<GroupDTO> groups = Collections.emptyList();
    ArgumentCaptor<UserDetails> userDetailsCaptor = ArgumentCaptor.forClass(UserDetails.class);
    when(groupService.getGroupsBasedOnUserDetails(userDetailsCaptor.capture())).thenReturn(groups);

    mockMvc.perform(get("/groups"))
        .andExpect(status().isOk())
        .andExpect(view().name("groups"))
        .andExpect(model().attribute("groups", groups))
        .andExpect(model().attributeExists("group"))
        .andExpect(model().attribute("group", instanceOf(GroupDTO.class)));

    verify(groupService).getGroupsBasedOnUserDetails(userDetailsCaptor.getValue());

    UserDetails capturedUserDetails = userDetailsCaptor.getValue();
    assertThat(capturedUserDetails.getAuthorities()).anyMatch(
        a -> a.getAuthority().equals("ROLE_ADMIN"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void editGroupShouldReturnGroupEditViewWhenGroupExists() throws Exception {
    String groupId = "1";
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setId(groupId);
    groupDTO.setName("Test Group");
    groupDTO.setAvailableCourseAssignments(Collections.singletonList(new CourseAssignmentDTO()));
    groupDTO.setAvailableStudents(Collections.singletonList(new StudentResponseDTO()));

    when(groupService.getGroupEditDetails(groupId)).thenReturn(groupDTO);

    mockMvc.perform(get("/groups/edit/{id}", groupId))
        .andExpect(status().isOk())
        .andExpect(view().name("group-edit"))
        .andExpect(model().attribute("group", groupDTO))
        .andExpect(
            model().attribute("availableAssignments", groupDTO.getAvailableCourseAssignments()))
        .andExpect(model().attribute("availableStudents", groupDTO.getAvailableStudents()));

    verify(groupService).getGroupEditDetails(groupId);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void editGroupShouldRedirectToGroupsWhenGroupNotFound() throws Exception {
    String groupId = "nonexistent";
    when(groupService.getGroupEditDetails(groupId)).thenThrow(new EntityNotFoundException());

    mockMvc.perform(get("/groups/edit/{id}", groupId))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/groups"))
        .andExpect(flash().attribute("errorMessage", "Group not found"));

    verify(groupService).getGroupEditDetails(groupId);
  }

  @Test
  @WithMockUser(roles = "USER")
  void editGroupShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(get("/groups/edit/1"))
        .andExpect(status().isForbidden());

    verifyNoInteractions(groupService);
  }

  @Test
  void editGroup_ShouldRedirectToLogin_WhenUserIsNotAuthenticated() throws Exception {
    mockMvc.perform(get("/groups/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verifyNoInteractions(groupService);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void showAddGroupFormShouldReturnGroupsViewWhenUserIsAdmin() throws Exception {
    mockMvc.perform(get("/groups/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("groups"))
        .andExpect(model().attributeExists("group"))
        .andExpect(model().attribute("group", instanceOf(GroupDTO.class)));
  }

  @Test
  @WithMockUser(roles = "USER")
  void showAddGroupFormShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(get("/groups/add"))
        .andExpect(status().isForbidden());
  }

  @Test
  void showAddGroupFormShouldRedirectToLoginWhenUserIsNotAuthenticated() throws Exception {
    mockMvc.perform(get("/groups/add"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void addGroupShouldRedirectToGroupsWhenInputIsValid() throws Exception {
    String validGroupName = "FLA-123";

    mockMvc.perform(post("/groups/add")
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("name", validGroupName))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/groups"));

    verify(groupService).addGroup(argThat(group -> group.getName().equals(validGroupName)));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void addGroupShouldReturnGroupsViewWhenInputIsInvalid() throws Exception {
    List<GroupDTO> groups = Arrays.asList(new GroupDTO(), new GroupDTO());
    when(groupService.getAllGroups()).thenReturn(groups);

    mockMvc.perform(post("/groups/add")
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("name", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("groups"))
        .andExpect(model().attributeExists("groups"))
        .andExpect(model().hasErrors());

    verify(groupService).getAllGroups();
    verify(groupService, never()).addGroup(any(GroupDTO.class));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void addGroupShouldReturnGroupsViewWhenGroupAlreadyExists() throws Exception {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setName("FLA-123");

    List<GroupDTO> groups = Arrays.asList(new GroupDTO(), new GroupDTO());
    when(groupService.getAllGroups()).thenReturn(groups);
    doThrow(new GroupAlreadyExistsException("Group with name FLA-123 already exists"))
        .when(groupService).addGroup(any(GroupDTO.class));

    mockMvc.perform(post("/groups/add")
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("name", "FLA-123"))
        .andExpect(status().isOk())
        .andExpect(view().name("groups"))
        .andExpect(model().attributeExists("groups"))
        .andExpect(model().attributeHasFieldErrors("group", "name"));

    verify(groupService).getAllGroups();
    verify(groupService).addGroup(any(GroupDTO.class));
  }

  @Test
  @WithMockUser(roles = "USER")
  void addGroupShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(post("/groups/add")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("name", "Test Group"))
        .andExpect(status().isForbidden());

    verifyNoInteractions(groupService);
  }

  @Test
  void addGroupShouldRedirectToLoginWhenUserIsNotAuthenticated() throws Exception {
    mockMvc.perform(post("/groups/add")
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("name", "Test Group"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verifyNoInteractions(groupService);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getAvailableCourseAssignments_ShouldReturnListOfAssignments_WhenAssignmentsExist()
      throws Exception {
    CourseAssignmentDTO assignment1 = new CourseAssignmentDTO();
    assignment1.setId("1");
    assignment1.setCourseName("Course 1");

    CourseAssignmentDTO assignment2 = new CourseAssignmentDTO();
    assignment2.setId("2");
    assignment2.setCourseName("Course 2");

    when(groupService.getCourseAssignmentWithNoGroup()).thenReturn(
        Arrays.asList(assignment1, assignment2));

    mockMvc.perform(get("/groups/edit/available-courseAssignments")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is("1")))
        .andExpect(jsonPath("$[0].courseName", is("Course 1")))
        .andExpect(jsonPath("$[1].id", is("2")))
        .andExpect(jsonPath("$[1].courseName", is("Course 2")));

    verify(groupService).getCourseAssignmentWithNoGroup();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getAvailableCourseAssignments_ShouldReturnEmptyList_WhenNoAssignmentsExist()
      throws Exception {
    when(groupService.getCourseAssignmentWithNoGroup()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/groups/edit/available-courseAssignments")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(0)));

    verify(groupService).getCourseAssignmentWithNoGroup();
  }

  @Test
  @WithMockUser(roles = "USER")
  void getAvailableCourseAssignments_ShouldReturnForbidden_WhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(get("/groups/edit/available-courseAssignments")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

    verify(groupService, never()).getCourseAssignmentWithNoGroup();
  }

  @Test
  void getAvailableCourseAssignments_ShouldRedirectToLogin_WhenUserIsNotAuthenticated()
      throws Exception {
    mockMvc.perform(get("/groups/edit/available-courseAssignments")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verify(groupService, never()).getCourseAssignmentWithNoGroup();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void addCourseAssignmentToGroupShouldAddCourseAssignmentToGroupSuccessfully() throws Exception {
    String groupId = "group1";
    String assignmentId = "assignment1";

    doNothing().when(groupService).addCourseAssignmentToGroup(groupId, assignmentId);

    mockMvc.perform(post("/groups/edit/{groupId}/addCourseAssignment", groupId)
            .param("assignmentId", assignmentId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk());

    verify(groupService).addCourseAssignmentToGroup(groupId, assignmentId);
  }

  @Test
  @WithMockUser(roles = "USER")
  void addCourseAssignmentToGroupShouldReturnForbiddenForNonAdminUser() throws Exception {
    String groupId = "group1";
    String assignmentId = "assignment1";

    mockMvc.perform(post("/groups/edit/{groupId}/addCourseAssignment", groupId)
            .param("assignmentId", assignmentId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isForbidden());

    verify(groupService, never()).addCourseAssignmentToGroup(anyString(), anyString());
  }

  @Test
  void addCourseAssignmentToGroupShouldRedirectToLoginForUnauthenticatedUser() throws Exception {
    String groupId = "group1";
    String assignmentId = "assignment1";

    mockMvc.perform(post("/groups/edit/{groupId}/addCourseAssignment", groupId)
            .param("assignmentId", assignmentId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verify(groupService, never()).addCourseAssignmentToGroup(anyString(), anyString());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void removeCourseAssignmentFromGroupShouldReturnOkWhenSuccessful() throws Exception {
    String groupId = "group1";
    String assignmentId = "assignment1";

    doNothing().when(groupService).removeCourseAssignmentFromGroup(assignmentId);

    mockMvc.perform(post("/groups/edit/{id}/remove-courseAssignment", groupId)
            .param("assignmentId", assignmentId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk());

    verify(groupService).removeCourseAssignmentFromGroup(assignmentId);
  }

  @Test
  @WithMockUser(roles = "USER")
  void removeCourseAssignmentFromGroupShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    String groupId = "group1";
    String assignmentId = "assignment1";

    mockMvc.perform(post("/groups/edit/{id}/remove-courseAssignment", groupId)
            .param("assignmentId", assignmentId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isForbidden());

    verify(groupService, never()).removeCourseAssignmentFromGroup(anyString());
  }

  @Test
  void removeCourseAssignmentFromGroupShouldRedirectToLoginWhenUserIsNotAuthenticated()
      throws Exception {
    String groupId = "group1";
    String assignmentId = "assignment1";

    mockMvc.perform(post("/groups/edit/{id}/remove-courseAssignment", groupId)
            .param("assignmentId", assignmentId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verify(groupService, never()).removeCourseAssignmentFromGroup(anyString());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getAvailableStudentsShouldReturnListOfStudentsWhenThereAreAvailableStudents()
      throws Exception {
    List<StudentResponseDTO> students = Arrays.asList(
        StudentResponseDTO.builder().id("1").firstName("John").lastName("Doe").email("john@example.com")
            .build(),
        StudentResponseDTO.builder().id("2").firstName("Jane").lastName("Doe").email("jane@example.com")
            .build());
    when(groupService.getStudentWithNoGroup()).thenReturn(students);

    mockMvc.perform(get("/groups/edit/available-students")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is("1")))
        .andExpect(jsonPath("$[0].firstName", is("John")))
        .andExpect(jsonPath("$[1].id", is("2")))
        .andExpect(jsonPath("$[1].firstName", is("Jane")));

    verify(groupService).getStudentWithNoGroup();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getAvailableStudentsShouldReturnEmptyListWhenNoStudentsAvailable() throws Exception {
    when(groupService.getStudentWithNoGroup()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/groups/edit/available-students")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(0)));

    verify(groupService).getStudentWithNoGroup();
  }

  @Test
  @WithMockUser(roles = "USER")
  void getAvailableStudentsShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(get("/groups/edit/available-students")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

    verify(groupService, never()).getStudentWithNoGroup();
  }

  @Test
  void getAvailableStudentsShouldRedirectToLoginWhenUserIsNotAuthenticated() throws Exception {
    mockMvc.perform(get("/groups/edit/available-students")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verify(groupService, never()).getStudentWithNoGroup();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void addStudentToGroupShouldReturnOkWhenSuccessful() throws Exception {
    String groupId = "group1";
    String studentId = "student1";

    doNothing().when(groupService).addStudentToGroup(groupId, studentId);

    mockMvc.perform(post("/groups/edit/{groupId}/addStudent", groupId)
            .with(csrf())
            .param("studentId", studentId)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk());

    verify(groupService).addStudentToGroup(groupId, studentId);
  }

  @Test
  @WithMockUser(roles = "USER")
  void addStudentToGroupShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    String groupId = "group1";
    String studentId = "student1";

    mockMvc.perform(post("/groups/edit/{groupId}/addStudent", groupId)
            .with(csrf())
            .param("studentId", studentId)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isForbidden());

    verify(groupService, never()).addStudentToGroup(anyString(), anyString());
  }

  @Test
  void addStudentToGroupShouldRedirectToLoginWhenUserIsNotAuthenticated() throws Exception {
    String groupId = "group1";
    String studentId = "student1";

    mockMvc.perform(post("/groups/edit/{groupId}/addStudent", groupId)
            .with(csrf())
            .param("studentId", studentId)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verify(groupService, never()).addStudentToGroup(anyString(), anyString());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void removeStudentFromGroupShouldReturnOkWhenSuccessful() throws Exception {
    String groupId = "group1";
    String studentId = "student1";

    doNothing().when(groupService).removeStudentFromGroup(studentId);

    mockMvc.perform(post("/groups/edit/{id}/remove-student", groupId)
            .with(csrf())
            .param("studentId", studentId)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk());

    verify(groupService).removeStudentFromGroup(studentId);
  }

  @Test
  @WithMockUser(roles = "USER")
  void removeStudentFromGroupShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    String groupId = "group1";
    String studentId = "student1";

    mockMvc.perform(post("/groups/edit/{id}/remove-student", groupId)
            .with(csrf())
            .param("studentId", studentId)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isForbidden());

    verify(groupService, never()).removeStudentFromGroup(anyString());
  }

  @Test
  void removeStudentFromGroupShouldRedirectToLoginWhenUserIsNotAuthenticated() throws Exception {
    String groupId = "group1";
    String studentId = "student1";

    mockMvc.perform(post("/groups/edit/{id}/remove-student", groupId)
            .with(csrf())
            .param("studentId", studentId)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verify(groupService, never()).removeStudentFromGroup(anyString());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void changeGroupStatusShouldReturnNewStatusWhenSuccessful() throws Exception {
    String groupId = "group1";
    GroupStatus newStatus = GroupStatus.ACTIVE;

    when(groupService.changeGroupStatus(groupId, newStatus)).thenReturn(newStatus);

    mockMvc.perform(post("/groups/edit/{id}/change-status", groupId)
            .with(csrf())
            .param("status", newStatus.name())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(content().string(newStatus.name()));

    verify(groupService).changeGroupStatus(groupId, newStatus);
  }

  @Test
  @WithMockUser(roles = "USER")
  void changeGroupStatusShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    String groupId = "group1";
    GroupStatus newStatus = GroupStatus.ACTIVE;

    mockMvc.perform(post("/groups/edit/{id}/change-status", groupId)
            .with(csrf())
            .param("status", newStatus.name())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isForbidden());

    verify(groupService, never()).changeGroupStatus(anyString(), any(GroupStatus.class));
  }

  @Test
  void changeGroupStatusShouldRedirectToLoginWhenUserIsNotAuthenticated() throws Exception {
    String groupId = "group1";
    GroupStatus newStatus = GroupStatus.ACTIVE;

    mockMvc.perform(post("/groups/edit/{id}/change-status", groupId)
            .with(csrf())
            .param("status", newStatus.name())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verify(groupService, never()).changeGroupStatus(anyString(), any(GroupStatus.class));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void changeGroupStatusShouldReturnBadRequestWhenStatusIsInvalid() throws Exception {
    String groupId = "group1";
    String invalidStatus = "INVALID_STATUS";

    mockMvc.perform(post("/groups/edit/{id}/change-status", groupId)
            .with(csrf())
            .param("status", invalidStatus)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isBadRequest());

    verify(groupService, never()).changeGroupStatus(anyString(), any(GroupStatus.class));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void checkIfGroupIsEmptyShouldReturnTrueWhenGroupHasNoStudents() throws Exception {
    String groupId = "emptyGroup";
    GroupDTO emptyGroup = new GroupDTO();
    emptyGroup.setStudents(Collections.emptyList());

    when(groupService.getGroupById(groupId)).thenReturn(Optional.of(emptyGroup));

    mockMvc.perform(get("/groups/edit/{groupId}/check-empty", groupId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));

    verify(groupService).getGroupById(groupId);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void checkIfGroupIsEmptyShouldReturnFalseWhenGroupHasStudents() throws Exception {
    String groupId = "nonEmptyGroup";
    GroupDTO nonEmptyGroup = new GroupDTO();
    nonEmptyGroup.setStudents(Arrays.asList(new StudentResponseDTO(), new StudentResponseDTO()));

    when(groupService.getGroupById(groupId)).thenReturn(Optional.of(nonEmptyGroup));

    mockMvc.perform(get("/groups/edit/{groupId}/check-empty", groupId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));

    verify(groupService).getGroupById(groupId);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void checkIfGroupIsEmptyShouldReturnFalseWhenGroupNotFound() throws Exception {
    String groupId = "nonexistentGroup";

    when(groupService.getGroupById(groupId)).thenReturn(Optional.empty());

    mockMvc.perform(get("/groups/edit/{groupId}/check-empty", groupId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));

    verify(groupService).getGroupById(groupId);
  }

  @Test
  @WithMockUser(roles = "USER")
  void checkIfGroupIsEmptyShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    String groupId = "anyGroup";

    mockMvc.perform(get("/groups/edit/{groupId}/check-empty", groupId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

    verify(groupService, never()).getGroupById(anyString());
  }

  @Test
  void checkIfGroupIsEmptyShouldRedirectToLoginWhenUserIsNotAuthenticated() throws Exception {
    String groupId = "anyGroup";

    mockMvc.perform(get("/groups/edit/{groupId}/check-empty", groupId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verify(groupService, never()).getGroupById(anyString());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteGroupShouldReturnOkWhenGroupExistsAndStatusIsDeleted() throws Exception {
    String groupId = "deletedGroup";
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setStatus(GroupStatus.DELETED);

    when(groupService.getGroupById(groupId)).thenReturn(Optional.of(groupDTO));
    doNothing().when(groupService).deleteGroup(groupId);

    mockMvc.perform(delete("/groups/delete/{id}", groupId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(groupService).getGroupById(groupId);
    verify(groupService).deleteGroup(groupId);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteGroupShouldReturnBadRequestWhenGroupExistsButStatusIsNotDeleted() throws Exception {
    String groupId = "activeGroup";
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setStatus(GroupStatus.ACTIVE);

    when(groupService.getGroupById(groupId)).thenReturn(Optional.of(groupDTO));

    mockMvc.perform(delete("/groups/delete/{id}", groupId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    verify(groupService).getGroupById(groupId);
    verify(groupService, never()).deleteGroup(anyString());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteGroupShouldReturnBadRequestWhenGroupDoesNotExist() throws Exception {
    String groupId = "nonexistentGroup";

    when(groupService.getGroupById(groupId)).thenReturn(Optional.empty());

    mockMvc.perform(delete("/groups/delete/{id}", groupId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    verify(groupService).getGroupById(groupId);
    verify(groupService, never()).deleteGroup(anyString());
  }

  @Test
  @WithMockUser(roles = "USER")
  void deleteGroupShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    String groupId = "anyGroup";

    mockMvc.perform(delete("/groups/delete/{id}", groupId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

    verify(groupService, never()).getGroupById(anyString());
    verify(groupService, never()).deleteGroup(anyString());
  }

  @Test
  void deleteGroupShouldRedirectToLoginWhenUserIsNotAuthenticated() throws Exception {
    String groupId = "anyGroup";

    mockMvc.perform(delete("/groups/delete/{id}", groupId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));

    verify(groupService, never()).getGroupById(anyString());
    verify(groupService, never()).deleteGroup(anyString());
  }

}
