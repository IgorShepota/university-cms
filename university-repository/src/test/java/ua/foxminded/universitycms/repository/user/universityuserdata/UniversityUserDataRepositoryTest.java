package ua.foxminded.universitycms.repository.user.universityuserdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.UniversityUserData;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class UniversityUserDataRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private UniversityUserDataRepository universityUserDataRepository;

  @Test
  void findByIdShouldReturnUniversityUserDataWhenIdExists() {
    String expectedId = "1a9d5f8e-a8d8-11ed-a8fc-0242ac120002";

    Optional<UniversityUserData> optionalUniversityUserData = universityUserDataRepository.findById(
        expectedId);

    assertThat(optionalUniversityUserData).isPresent();
    UniversityUserData universityUserData = optionalUniversityUserData.get();
    assertThat(universityUserData.getId()).isEqualTo(expectedId);
  }

}
