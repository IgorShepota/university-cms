package ua.foxminded.universitycms.repository.user.universityuserdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.AdminData;
import ua.foxminded.universitycms.repository.AbstractRepositoryTest;

class AdminDataRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private AdminDataRepository adminDataRepository;

  @Test
  void findByIdShouldReturnAdminDataWhenIdExists() {
    String expectedId = "1a9d5f8e-a8d8-11ed-a8fc-0242ac120001";

    Optional<AdminData> optionalAdminData = adminDataRepository.findById(expectedId);

    assertThat(optionalAdminData).isPresent();
    AdminData adminData = optionalAdminData.get();
    assertThat(adminData.getId()).isEqualTo(expectedId);
  }

}
