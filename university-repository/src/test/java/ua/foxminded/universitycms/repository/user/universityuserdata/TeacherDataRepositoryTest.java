package ua.foxminded.universitycms.repository.user.universityuserdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class TeacherDataRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private TeacherDataRepository teacherDataRepository;

  @Test
  void findByIdShouldReturnTeacherDataWhenIdExists() {
    String expectedId = "1a9d5f8e-a8d8-11ed-a8fc-0242ac120004";

    Optional<TeacherData> optionalTeacherData = teacherDataRepository.findById(expectedId);

    assertThat(optionalTeacherData).isPresent();
    TeacherData teacherData = optionalTeacherData.get();
    assertThat(teacherData.getId()).isEqualTo(expectedId);
  }

}
