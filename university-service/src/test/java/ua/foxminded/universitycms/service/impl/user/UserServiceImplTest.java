package ua.foxminded.universitycms.service.impl.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.dto.user.role.StudentDTO;
import ua.foxminded.universitycms.mapping.user.UserMapper;
import ua.foxminded.universitycms.mapping.user.role.StudentMapper;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.AdminData;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.UniversityUserData;
import ua.foxminded.universitycms.repository.user.UserRepository;
import ua.foxminded.universitycms.repository.user.role.RoleRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.StudentDataRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.UniversityUserDataRepository;
import ua.foxminded.universitycms.service.exception.InvalidRoleNameException;
import ua.foxminded.universitycms.service.exception.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserMapper userMapper;

  @Mock
  private StudentMapper studentMapper;

  @Mock
  private UserRepository userRepository;

  @Mock
  private StudentDataRepository studentDataRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private UniversityUserDataRepository universityUserDataRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void registerUserShouldSaveUserWithUnverifiedRole() {
    UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
    User user = new User();
    Role unverifiedRole = new Role(null, RoleName.UNVERIFIED, null);

    when(userMapper.userRegistrationDTOToUser(userRegistrationDTO)).thenReturn(user);
    when(roleRepository.findByName(RoleName.UNVERIFIED)).thenReturn(Optional.of(unverifiedRole));

    userService.registerUser(userRegistrationDTO);

    verify(userRepository).save(user);
    assertThat(user.getRole()).isEqualTo(unverifiedRole);
  }

  @Test
  void registerUserShouldThrowExceptionWhenUnverifiedRoleNotFound() {
    UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
    User user = new User();

    when(userMapper.userRegistrationDTOToUser(userRegistrationDTO)).thenReturn(user);
    when(roleRepository.findByName(RoleName.UNVERIFIED)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userService.registerUser(userRegistrationDTO))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Unverified role not found");

    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void getUserByIdShouldReturnUserDTO() {
    String id = "user-id";
    User user = new User();
    UserDTO userDTO = new UserDTO();

    when(userRepository.findById(id)).thenReturn(Optional.of(user));
    when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

    Optional<UserDTO> result = userService.getUserById(id);

    assertThat(result).isPresent().contains(userDTO);
  }

  @Test
  void getAllUsersShouldReturnListOfUserDTOs() {
    List<User> users = Arrays.asList(new User(), new User());
    List<UserDTO> userDTOs = Arrays.asList(new UserDTO(), new UserDTO());

    when(userRepository.findAll()).thenReturn(users);
    when(userMapper.userToUserDTO(users.get(0))).thenReturn(userDTOs.get(0));
    when(userMapper.userToUserDTO(users.get(1))).thenReturn(userDTOs.get(1));

    List<UserDTO> result = userService.getAllUsers();

    assertThat(result).isEqualTo(userDTOs);
  }

  @Test
  void getAllStudentsShouldReturnListOfStudentDTOs() {
    User studentUser1 = User.builder().id("1").build();
    User studentUser2 = User.builder().id("2").build();
    List<User> studentUsers = Arrays.asList(studentUser1, studentUser2);

    StudentData studentData1 = StudentData.builder().withId("1").build();
    StudentData studentData2 = StudentData.builder().withId("2").build();
    List<StudentData> studentDataList = Arrays.asList(studentData1, studentData2);

    StudentDTO studentDTO1 = StudentDTO.builder().id("1").build();
    StudentDTO studentDTO2 = StudentDTO.builder().id("2").build();

    when(userRepository.findAllByRoleName(RoleName.STUDENT)).thenReturn(studentUsers);
    when(studentDataRepository.findAll()).thenReturn(studentDataList);
    when(studentMapper.mapToStudentDTO(studentUser1, studentData1)).thenReturn(studentDTO1);
    when(studentMapper.mapToStudentDTO(studentUser2, studentData2)).thenReturn(studentDTO2);

    List<StudentDTO> result = userService.getAllStudents();

    verify(userRepository).findAllByRoleName(RoleName.STUDENT);
    verify(studentDataRepository).findAll();
    verify(studentMapper).mapToStudentDTO(studentUser1, studentData1);
    verify(studentMapper).mapToStudentDTO(studentUser2, studentData2);

    assertThat(result).containsExactly(studentDTO1, studentDTO2);
  }

  @Test
  void getAllUsersSortedShouldReturnAscendingOrderWhenDataCorrect() {
    String sortField = "firstName";
    User user1 = User.builder().firstName("Alice").lastName("Wonderland").build();
    User user2 = User.builder().firstName("Bob").lastName("Builder").build();
    List<User> users = Arrays.asList(user1, user2);

    when(userRepository.findAll(Sort.by(Direction.ASC, sortField))).thenReturn(users);
    when(userMapper.userToUserDTO(user1)).thenReturn(
        UserDTO.builder().firstName("Alice").lastName("Wonderland").build());
    when(userMapper.userToUserDTO(user2)).thenReturn(
        UserDTO.builder().firstName("Bob").lastName("Builder").build());

    List<UserDTO> sortedUsers = userService.getAllUsersSorted(sortField, "asc");

    assertThat(sortedUsers)
        .extracting(UserDTO::getFirstName)
        .as("Users should be sorted by firstName in ascending order")
        .containsExactly("Alice", "Bob");

    verify(userRepository).findAll(Sort.by(Direction.ASC, sortField));
    verify(userMapper).userToUserDTO(user1);
    verify(userMapper).userToUserDTO(user2);
  }

  @Test
  void getAllUsersSortedShouldReturnDescendingOrderWhenDataCorrect() {
    String sortField = "firstName";
    User user1 = User.builder().firstName("Bob").lastName("Builder").build();
    User user2 = User.builder().firstName("Alice").lastName("Wonderland").build();
    List<User> users = Arrays.asList(user1, user2);

    when(userRepository.findAll(Sort.by(Direction.DESC, sortField))).thenReturn(users);
    when(userMapper.userToUserDTO(user1)).thenReturn(
        UserDTO.builder().firstName("Bob").lastName("Builder").build());
    when(userMapper.userToUserDTO(user2)).thenReturn(
        UserDTO.builder().firstName("Alice").lastName("Wonderland").build());

    List<UserDTO> sortedUsers = userService.getAllUsersSorted(sortField, "desc");

    assertThat(sortedUsers)
        .extracting(UserDTO::getFirstName)
        .as("Users should be sorted by firstName in descending order")
        .containsExactly("Bob", "Alice");

    verify(userRepository).findAll(Sort.by(Direction.DESC, sortField));
    verify(userMapper).userToUserDTO(user1);
    verify(userMapper).userToUserDTO(user2);
  }

  @Test
  void getAllTeachersShouldReturnListOfUserDTOs() {
    User teacherUser1 = User.builder()
        .role(Role.builder().withName(RoleName.TEACHER).build())
        .build();

    User teacherUser2 = User.builder()
        .role(Role.builder().withName(RoleName.TEACHER).build())
        .build();

    UserDTO teacherUserDTO1 = new UserDTO();
    UserDTO teacherUserDTO2 = new UserDTO();

    List<User> teacherUsers = Arrays.asList(teacherUser1, teacherUser2);

    when(userRepository.findAllByRoleName(RoleName.TEACHER)).thenReturn(teacherUsers);
    when(userMapper.userToUserDTO(teacherUser1)).thenReturn(teacherUserDTO1);
    when(userMapper.userToUserDTO(teacherUser2)).thenReturn(teacherUserDTO2);

    List<UserDTO> result = userService.getAllTeachers();

    assertThat(result).containsExactly(teacherUserDTO1, teacherUserDTO2);
  }

  @Test
  void getAllUsersShouldReturnPageOfUserDTOs() {
    int page = 1;
    int itemsPerPage = 2;
    User user1 = new User();
    User user2 = new User();
    UserDTO userDTO1 = new UserDTO();
    UserDTO userDTO2 = new UserDTO();

    List<User> users = Arrays.asList(user1, user2);
    Page<User> userPage = new PageImpl<>(users);

    when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
    when(userMapper.userToUserDTO(user1)).thenReturn(userDTO1);
    when(userMapper.userToUserDTO(user2)).thenReturn(userDTO2);

    List<UserDTO> result = userService.getAllUsers(page, itemsPerPage);

    assertThat(result).containsExactly(userDTO1, userDTO2);
  }

  @Test
  void updateUserRoleShouldWorkCorrectlyWithCorrectData() {
    String userId = "valid-user-id";
    String newRoleName = "STUDENT";

    User user = new User();
    user.setRole(
        Role.builder().withName(RoleName.UNVERIFIED).withUsers(Collections.singletonList(user))
            .build());
    user.setUniversityUserData(null);

    Role newRole = Role.builder().withName(RoleName.STUDENT).build();

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findByName(RoleName.valueOf(newRoleName))).thenReturn(Optional.of(newRole));

    userService.updateUserRole(userId, newRoleName);

    verify(userRepository, times(1)).findById(userId);
    verify(roleRepository, times(1)).findByName(RoleName.valueOf(newRoleName));
    verify(universityUserDataRepository, times(1)).save(any(StudentData.class));
    verify(userRepository, times(1)).saveAndFlush(user);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void updateUserRoleShouldThrowsExceptionIfUserIdIsInvalid() {
    String invalidUserId = "invalid-id";
    when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userService.updateUserRole(invalidUserId, "STUDENT"))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("Invalid user ID");

    verify(userRepository).findById(invalidUserId);
    verifyNoMoreInteractions(roleRepository);
  }

  @Test
  void updateUserRoleShouldThrowsExceptionIfRoleNameIsInvalid() {
    String userId = "userId";
    String newRoleName = "ADMIN";

    Role role = new Role();
    role.setName(RoleName.ADMIN);

    User user = new User();
    user.setId(userId);
    user.setRole(role);

    when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

    when(roleRepository.findByName(ArgumentMatchers.any(RoleName.class))).thenReturn(
        java.util.Optional.empty());

    assertThatThrownBy(() -> userService.updateUserRole(userId, newRoleName))
        .isInstanceOf(InvalidRoleNameException.class)
        .hasMessage("Invalid role name: " + newRoleName);
  }

  @Test
  void updateUserRoleShouldWorkOnlyIfUserRoleIsUnverified() {
    String userId = "valid-user-id";
    String newRoleName = "STUDENT";

    User user = new User();
    user.setRole(Role.builder()
        .withName(RoleName.UNVERIFIED)
        .withUsers(Collections.singletonList(user))
        .build());
    user.setUniversityUserData(new TeacherData());

    Role newRole = Role.builder()
        .withName(RoleName.STUDENT)
        .build();

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findByName(RoleName.valueOf(newRoleName))).thenReturn(Optional.of(newRole));

    userService.updateUserRole(userId, newRoleName);

    verify(userRepository, times(1)).findById(userId);
    verify(roleRepository, times(1)).findByName(RoleName.valueOf(newRoleName));
    verify(universityUserDataRepository, never()).save(
        any(UniversityUserData.class));
    verify(userRepository, never()).saveAndFlush(user);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void updateUserRoleShouldWorkOnlyIfUniversityUserDataIsNull() {
    String userId = "valid-user-id";
    String newRoleName = "TEACHER";

    User user = new User();
    user.setRole(Role.builder()
        .withName(RoleName.STUDENT)
        .withUsers(Collections.singletonList(user))
        .build());
    user.setUniversityUserData(null);

    Role newRole = Role.builder()
        .withName(RoleName.TEACHER)
        .build();

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findByName(RoleName.valueOf(newRoleName))).thenReturn(Optional.of(newRole));

    userService.updateUserRole(userId, newRoleName);

    verify(userRepository, times(1)).findById(userId);
    verify(roleRepository, times(1)).findByName(RoleName.valueOf(newRoleName));
    verify(universityUserDataRepository, never()).save(any(UniversityUserData.class));
    verify(userRepository, never()).saveAndFlush(user);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void updateUserRoleShouldSetRoleToAdminIfDataCorrect() {
    String userId = "user123";
    String newRoleName = "ADMIN";
    User user = new User();
    user.setId(userId);
    user.setRole(Role.builder()
        .withName(RoleName.UNVERIFIED)
        .withUsers(Collections.singletonList(user))
        .build());
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findByName(RoleName.ADMIN)).thenReturn(
        Optional.of(Role.builder()
            .withName(RoleName.ADMIN)
            .withUsers(Collections.singletonList(user))
            .build()));

    userService.updateUserRole(userId, newRoleName);

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCaptor.capture());
    User capturedUser = userCaptor.getValue();
    assertThat(capturedUser.getRole().getName()).isEqualTo(RoleName.ADMIN);
    verify(universityUserDataRepository).save(any(AdminData.class));
  }

  @Test
  public void updateUserRoleShouldSetRoleToTeacherIfDataCorrect() {
    String userId = "user123";
    String newRoleName = "TEACHER";
    User user = new User();
    user.setId(userId);
    user.setRole(Role.builder()
        .withName(RoleName.UNVERIFIED)
        .withUsers(Collections.singletonList(user))
        .build());
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findByName(RoleName.TEACHER)).thenReturn(
        Optional.of(Role.builder()
            .withName(RoleName.TEACHER)
            .withUsers(Collections.singletonList(user))
            .build()));

    userService.updateUserRole(userId, newRoleName);

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCaptor.capture());
    User capturedUser = userCaptor.getValue();
    assertThat(capturedUser.getRole().getName()).isEqualTo(RoleName.TEACHER);

    verify(universityUserDataRepository).save(any(TeacherData.class));
  }

}
