package ua.foxminded.universitycms.repository.user.roles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.roles.Student;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class StudentRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;

  @Test
  void findByIdShouldReturnStudentWhenIdExists() {
    String expectedId = "8d849109-9ba5-46a5-8807-ba093306a7b8";
    Optional<Student> optionalStudent = studentRepository.findById(
        expectedId);
    assertThat(optionalStudent).isPresent();
    Student student = optionalStudent.get();
    assertThat(student.getId()).isEqualTo(expectedId);
  }

}
