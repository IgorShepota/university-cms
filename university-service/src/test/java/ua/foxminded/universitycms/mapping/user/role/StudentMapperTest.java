package ua.foxminded.universitycms.mapping.user.role;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.user.Gender;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;

class StudentMapperTest {

  private final StudentMapper mapper = new StudentMapperImpl();

  @Test
  void mapToStudentResponseDTOShouldMapCorrectlyIfDataIsCorrectResponse() {
    Role role = Role.builder()
        .withName(RoleName.STUDENT)
        .build();

    User user = User.builder()
        .id("1")
        .email("test@example.com")
        .firstName("John")
        .lastName("Doe")
        .gender(Gender.MALE)
        .role(role)
        .build();

    Group group = Group.builder()
        .withId("1")
        .withName("Group 1")
        .build();

    StudentData studentData = StudentData.builder()
        .withOwnerGroup(group)
        .withUser(user)
        .build();

    StudentResponseDTO studentDTO = mapper.mapToStudentResponseDTO(studentData);

    assertThat(studentDTO.getId()).isEqualTo(user.getId());
    assertThat(studentDTO.getEmail()).isEqualTo(user.getEmail());
    assertThat(studentDTO.getFirstName()).isEqualTo(user.getFirstName());
    assertThat(studentDTO.getLastName()).isEqualTo(user.getLastName());
    assertThat(studentDTO.getGender()).isEqualTo(user.getGender());
    assertThat(studentDTO.getRoleName()).isEqualTo(user.getRole().getName().name());
    assertThat(studentDTO.getGroupName()).isEqualTo(studentData.getOwnerGroup().getName());
  }

  @Test
  void mapToStudentResponseDTOShouldReturnNullIfStudentResponseDataIsNull() {
    StudentResponseDTO studentDTO = mapper.mapToStudentResponseDTO(null);

    assertThat(studentDTO).isNull();
  }

  @Test
  void userRoleNameShouldReturnNullIfRoleNameIsNull() {
    Role role = Role.builder()
        .withName(null)
        .build();

    User user = User.builder()
        .role(role)
        .build();

    Group group = new Group();

    StudentData studentData = StudentData.builder()
        .withOwnerGroup(group)
        .withUser(user)
        .build();

    StudentResponseDTO studentDTO = mapper.mapToStudentResponseDTO(studentData);

    assertThat(studentDTO.getRoleName()).isNull();
  }

  @Test
  void userRoleNameShouldReturnNullIfRoleIsNull() {
    User user = new User();

    Group group = new Group();

    StudentData studentData = StudentData.builder()
        .withOwnerGroup(group)
        .withUser(user)
        .build();

    StudentResponseDTO studentDTO = mapper.mapToStudentResponseDTO(studentData);

    assertThat(studentDTO.getRoleName()).isNull();
  }

  @Test
  void studentDataOwnerGroupNameShouldReturnNullIfOwnerGroupNameIsNull() {
    User user = new User();

    Group group = Group.builder()
        .withId("1")
        .withName(null)
        .build();

    StudentData studentData = StudentData.builder()
        .withOwnerGroup(group)
        .build();

    StudentResponseDTO studentDTO = mapper.mapToStudentResponseDTO(studentData);

    assertThat(studentDTO.getGroupName()).isNull();
  }

  @Test
  void studentDataOwnerGroupNameShouldReturnNullIfOwnerGroupIsNull() {
    User user = new User();

    StudentData studentData = StudentData.builder()
        .withOwnerGroup(null)
        .build();

    StudentResponseDTO studentDTO = mapper.mapToStudentResponseDTO(studentData);

    assertThat(studentDTO.getGroupName()).isNull();
  }

}
