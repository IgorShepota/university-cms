package ua.foxminded.universitycms.repository.user.universityuserdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class StudentDataRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private StudentDataRepository studentDataRepository;

  @Test
  void findByIdShouldReturnStudentDataWhenIdExists() {
    String expectedId = "1a9d5f8e-a8d8-11ed-a8fc-0242ac120003";

    Optional<StudentData> optionalStudentData = studentDataRepository.findById(expectedId);

    assertThat(optionalStudentData).isPresent();
    StudentData studentData = optionalStudentData.get();
    assertThat(studentData.getId()).isEqualTo(expectedId);
  }

}
