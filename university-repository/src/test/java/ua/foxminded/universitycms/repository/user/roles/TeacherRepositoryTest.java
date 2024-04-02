package ua.foxminded.universitycms.repository.user.roles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.roles.Teacher;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class TeacherRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private TeacherRepository teacherRepository;

  @Test
  void findByIdShouldReturnTeacherWhenIdExists() {
    String expectedId = "a7b4c6d8-e2f3-471b-8f62-3e2a7d9c3f5e";
    Optional<Teacher> optionalTeacher = teacherRepository.findById(
        expectedId);
    assertThat(optionalTeacher).isPresent();
    Teacher teacher = optionalTeacher.get();
    assertThat(teacher.getId()).isEqualTo(expectedId);
  }

}
