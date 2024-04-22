package ua.foxminded.universitycms.service.impl.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.dto.user.role.StudentDTO;
import ua.foxminded.universitycms.mapping.user.UserMapper;
import ua.foxminded.universitycms.mapping.user.role.StudentMapper;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;
import ua.foxminded.universitycms.repository.user.UserRepository;
import ua.foxminded.universitycms.repository.user.role.RoleRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.StudentDataRepository;

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

}
