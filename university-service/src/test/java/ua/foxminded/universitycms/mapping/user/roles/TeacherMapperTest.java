package ua.foxminded.universitycms.mapping.user.roles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.user.roles.TeacherDTO;
import ua.foxminded.universitycms.model.entity.user.roles.Teacher;

class TeacherMapperTest {

  private final TeacherMapper mapper = new TeacherMapperImpl();

  @Test
  void teacherToTeacherDTOShouldMapCorrectlyIfDataIsCorrect() {
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID().toString());
    teacher.setEmail("teacher@example.com");
    teacher.setFirstName("John");
    teacher.setLastName("Smith");
    teacher.setGender("Male");

    TeacherDTO teacherDTO = mapper.teacherToTeacherDTO(teacher);

    assertThat(teacherDTO.getId()).isEqualTo(teacher.getId());
    assertThat(teacherDTO.getEmail()).isEqualTo(teacher.getEmail());
    assertThat(teacherDTO.getFirstName()).isEqualTo(teacher.getFirstName());
    assertThat(teacherDTO.getLastName()).isEqualTo(teacher.getLastName());
    assertThat(teacherDTO.getGender()).isEqualTo(teacher.getGender());
  }

  @Test
  void teacherDTOToTeacherShouldReturnNotNullWhenDataIsCorrect() {
    TeacherDTO teacherDTO = new TeacherDTO();
    teacherDTO.setId(UUID.randomUUID().toString());
    teacherDTO.setEmail("example@teacher.com");
    teacherDTO.setFirstName("Jane");
    teacherDTO.setLastName("Doe");
    teacherDTO.setGender("Female");

    Teacher teacher = mapper.teacherDTOToTeacher(teacherDTO);

    assertThat(teacher).isNotNull();
  }

  @Test
  void teacherToTeacherDTOShouldReturnNullWhenTeacherIsNull() {
    assertThat(mapper.teacherToTeacherDTO(null)).isNull();
  }

  @Test
  void teacherDTOToTeacherShouldReturnNullWhenTeacherDTOIsNull() {
    assertThat(mapper.teacherDTOToTeacher(null)).isNull();
  }

}
