package ua.foxminded.universitycms.mapping.user.roles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.user.roles.StudentDTO;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.user.roles.Student;

class StudentMapperTest {

  private final StudentMapper mapper = new StudentMapperImpl();

  @Test
  void studentToStudentDTOShouldMapCorrectlyIfDataIsCorrect() {
    Student student = new Student();
    Group group = new Group();
    group.setName("Computer Science");
    student.setOwnerGroup(group);
    student.setId(UUID.randomUUID().toString());
    student.setEmail("student@example.com");
    student.setFirstName("John");
    student.setLastName("Doe");
    student.setGender("Male");

    StudentDTO studentDTO = mapper.studentToStudentDTO(student);

    assertThat(studentDTO.getGroupName()).isEqualTo(group.getName());
    assertThat(studentDTO.getId()).isEqualTo(student.getId());
    assertThat(studentDTO.getEmail()).isEqualTo(student.getEmail());
    assertThat(studentDTO.getFirstName()).isEqualTo(student.getFirstName());
    assertThat(studentDTO.getLastName()).isEqualTo(student.getLastName());
    assertThat(studentDTO.getGender()).isEqualTo(student.getGender());
  }

  @Test
  void studentDTOToStudentShouldReturnNotNullWhenDataIsCorrect() {
    StudentDTO studentDTO = new StudentDTO();
    studentDTO.setGroupName("Computer Science");
    studentDTO.setId(UUID.randomUUID().toString());
    studentDTO.setEmail("example@student.com");
    studentDTO.setFirstName("Jane");
    studentDTO.setLastName("Smith");
    studentDTO.setGender("Female");

    Student student = mapper.studentDTOToStudent(studentDTO);

    assertThat(student).isNotNull();
  }

  @Test
  void studentToStudentDTOShouldReturnNullWhenStudentIsNull() {
    assertThat(mapper.studentToStudentDTO(null)).isNull();
  }

  @Test
  void studentDTOToStudentShouldReturnNullWhenStudentDTOIsNull() {
    assertThat(mapper.studentDTOToStudent(null)).isNull();
  }

  @Test
  void studentToStudentDTOShouldWorkCorrectlyWhenOwnerGroupIsNull() {
    Student student = new Student();
    student.setOwnerGroup(null);
    student.setId(UUID.randomUUID().toString());
    student.setEmail("nullgroup@student.com");
    student.setFirstName("Null");
    student.setLastName("Group");
    student.setGender("Non-binary");

    StudentDTO studentDTO = mapper.studentToStudentDTO(student);

    assertThat(studentDTO.getGroupName()).isNull();
  }

  @Test
  void studentToStudentDTOShouldWorkCorrectlyWhenOwnerGroupNameIsNull() {
    Student student = new Student();
    String uuid = UUID.randomUUID().toString();
    Group ownerGroup = new Group();

    student.setId(uuid);
    student.setOwnerGroup(ownerGroup);

    StudentDTO studentDTO = mapper.studentToStudentDTO(student);

    assertThat(studentDTO.getId()).isEqualTo(student.getId());
    assertThat(studentDTO.getGroupName()).isEqualTo(ownerGroup.getName());
  }

}
