package ua.foxminded.universitycms.service.user.roles;

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
import ua.foxminded.universitycms.entity.user.roles.Teacher;
import ua.foxminded.universitycms.repository.user.roles.TeacherRepository;

@SpringBootTest
class TeacherServiceTest {

  @MockBean
  private TeacherRepository teacherRepository;

  @Autowired
  private TeacherService teacherService;

  @Test
  void addTeacherShouldWorkCorrectlyIfTeacherEntityCorrect() {
    Teacher newTeacher = Teacher.builder().withId("1").withFirstName("John").withLastName("Doe")
        .withEmail("john.doe@example.com").withGender("Male").build();

    teacherService.addTeacher(newTeacher);

    verify(teacherRepository).save(newTeacher);
  }

  @Test
  void getTeacherByIdShouldReturnCorrectTeacherIfExists() {
    String teacherId = "1";
    Teacher mockTeacher = Teacher.builder().withId(teacherId).withFirstName("John")
        .withLastName("Doe").withEmail("john.doe@example.com").withGender("Male").build();

    when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(mockTeacher));

    Optional<Teacher> result = teacherService.getTeacherById(teacherId);

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(mockTeacher);
  }

  @Test
  void getTeacherByIdShouldReturnEmptyIfTeacherDoesNotExist() {
    String nonExistentId = "nonexistent";
    when(teacherRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    Optional<Teacher> result = teacherService.getTeacherById(nonExistentId);

    assertThat(result).isEmpty();
  }

  @Test
  void getAllTeachersShouldReturnAllTeachers() {
    List<Teacher> mockTeachers = Arrays.asList(
        Teacher.builder().withId("1").withFirstName("John").withLastName("Doe").build(),
        Teacher.builder().withId("2").withFirstName("Jane").withLastName("Doe").build());

    when(teacherRepository.findAll()).thenReturn(mockTeachers);

    List<Teacher> teachers = teacherService.getAllTeachers();

    assertThat(teachers).hasSize(2).isEqualTo(mockTeachers);
  }

  @Test
  void getAllTeachersWithPaginationShouldReturnCorrectData() {
    Teacher teacher1 = Teacher.builder().withId("1").withFirstName("John").withLastName("Doe")
        .build();
    Teacher teacher2 = Teacher.builder().withId("2").withFirstName("Jane").withLastName("Doe")
        .build();

    List<Teacher> teachers = Arrays.asList(teacher1, teacher2);
    Page<Teacher> teacherPage = new PageImpl<>(teachers);

    when(teacherRepository.findAll(any(Pageable.class))).thenReturn(teacherPage);

    List<Teacher> returnedTeachers = teacherService.getAllTeachers(1, 2);

    assertThat(returnedTeachers).hasSize(2).containsExactlyInAnyOrder(teacher1, teacher2);

    verify(teacherRepository).findAll(PageRequest.of(0, 2));
  }

  @Test
  void updateTeacherShouldCallSaveMethod() {
    Teacher teacherToUpdate = Teacher.builder().withId("1").withFirstName("UpdatedName")
        .withLastName("UpdatedLastName").build();

    teacherService.updateTeacher(teacherToUpdate);

    verify(teacherRepository).save(teacherToUpdate);
  }

  @Test
  void deleteTeacherShouldWorkCorrectlyIfTeacherExists() {
    String teacherId = "1";

    when(teacherRepository.existsById(teacherId)).thenReturn(true);

    boolean result = teacherService.deleteTeacher(teacherId);

    assertThat(result).isTrue();
    verify(teacherRepository).deleteById(teacherId);
  }

  @Test
  void deleteTeacherShouldReturnFalseIfTeacherNotExists() {
    String teacherId = "nonexistent";

    when(teacherRepository.existsById(teacherId)).thenReturn(false);

    boolean result = teacherService.deleteTeacher(teacherId);

    assertThat(result).isFalse();

  }
}
